<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Inventory List</title>

    <style>
        table {
            border-collapse: collapse;
            width: 60%;
        }
        th, td {
            padding: 8px 12px;
            border-bottom: 1px solid #ccc;
        }
        tr:hover {
            background-color: #f0f0f0;
            cursor: pointer;
        }
    </style>

    <script>
        function goToEdit(id) {
            window.location = '/inventory/edit/' + id;
        }
    </script>
</head>
<body>

<h1>Inventory</h1>

<g:if test="${products?.isEmpty()}">
    <p>No products available.</p>
</g:if>

<g:if test="${!products?.isEmpty()}">
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>SKU</th>
            </tr>
        </thead>
        <tbody>
            <g:each in="${products}" var="p" status="i">
                <tr onclick="goToEdit(${i})">
                    <td>${p.name}</td>
                    <td>${p.sku}</td>
                </tr>
            </g:each>
        </tbody>
    </table>
</g:if>

<p>
    <g:link action="create">Add New Product</g:link>
</p>

</body>
</html>
