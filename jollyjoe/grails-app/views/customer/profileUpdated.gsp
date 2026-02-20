<html>
<head>
    <meta http-equiv="refresh" content="5;url=${createLink(action:'show', id:customerId)}" />
    <title>Profile Updated</title>
</head>
<body>
    <h2>Profile updated successfully!</h2>
    <p>You will be redirected to your profile in 5 seconds.</p>
    <p>If not, <a href="${createLink(action:'show', id:customerId)}">click here</a>.</p>
</body>
</html>
