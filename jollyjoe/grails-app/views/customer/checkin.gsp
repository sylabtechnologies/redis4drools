<!doctype html>
<html>


<head>
	<title>Kiosk</title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">
	<script src="${resource(dir: 'javascript', file: 'respond.js')}" type="text/javascript"></script>
</head>

<body style="background-color: rgb(66, 70, 73);">
	<g:form url="[resource:customerInstance, action:'customerLookup']" >
			<g:render template="kioskForm"/>
	</g:form>
	
	
	<g:javascript library="jquery"/>
	<script src="${resource(dir: 'javascript', file: 'bootstrap.min.js')}" type="text/javascript"></script>
</body>
</html>