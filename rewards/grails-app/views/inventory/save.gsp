<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Product</title>
</head>
<body>

<h1>Create Product</h1>

<g:form controller="inventory" action="save" method="POST">
    <p>
        Name:
        <g:textField name="name" value="${product?.name}"/>
    </p>
    <p>
        SKU:
        <g:textField name="sku" value="${product?.sku}"/>
    </p>
    <p>
        <g:submitButton name="create" value="Create"/>
    </p>
</g:form>

</body>
</html>
