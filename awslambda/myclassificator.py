import base64
import json
import os

import boto3

rekognition = boto3.client("rekognition")

def lambda_handler(event, context):
    # 1) Get raw image bytes from Function URL event
    body = event.get("body", "")
    if not body:
        return {
            "statusCode": 400,
            "headers": {"content-type": "application/json"},
            "body": json.dumps({"error": "Missing request body (expected image bytes)."}),
        }

    if event.get("isBase64Encoded", False):
        image_bytes = base64.b64decode(body)
    else:
        # For real binary uploads this is usually base64-encoded.
        # This fallback is mainly for text payloads and won't represent arbitrary binary correctly.
        image_bytes = body.encode("utf-8")

    # 2) Call Rekognition
    min_confidence = float(os.environ.get("MIN_CONFIDENCE", "70"))
    max_labels = int(os.environ.get("MAX_LABELS", "50"))

    resp = rekognition.detect_labels(
        Image={"Bytes": image_bytes},
        MaxLabels=max_labels,
        MinConfidence=min_confidence,
    )

    labels = [
        {
            "name": l["Name"],
            "confidence": l["Confidence"],
            # Parents are optional; include if present
            "parents": [p["Name"] for p in l.get("Parents", [])],
            # Instances are optional; include if present
            "instances": [
                {
                    "confidence": i.get("Confidence"),
                    "bbox": i.get("BoundingBox"),
                }
                for i in l.get("Instances", [])
            ],
            # Categories are optional (some APIs/regions include them)
            "categories": [c["Name"] for c in l.get("Categories", [])] if "Categories" in l else [],
        }
        for l in resp.get("Labels", [])
    ]

    return {
        "statusCode": 200,
        "headers": {"content-type": "application/json"},
        "body": json.dumps(
            {
                "label_count": len(labels),
                "labels": labels,
            }
        ),
    }