import base64
import json

def lambda_handler(event, context):
    body = event.get("body", "")

    if event.get("isBase64Encoded", False):
        file_bytes = base64.b64decode(body)
    else:
        # Function URL passes body as a string; for raw binary uploads
        # you will typically see base64. This fallback handles text.
        file_bytes = body.encode("utf-8")

    size = len(file_bytes)

    return {
        "statusCode": 200,
        "headers": {"content-type": "application/json"},
        "body": json.dumps({"bytes": size}),
    }
