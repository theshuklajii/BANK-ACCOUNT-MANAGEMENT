<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Admin - Products</title>
    <link rel="stylesheet" href="../css/styles.css" />
</head>
<body>
<h2>Manage Products</h2>
<h3>Add New Product</h3>
<form method="post" action="products">
    <input type="hidden" name="action" value="add" />
    Name: <input type="text" name="name" required />
    Description: <input type="text" name="description" />
    Price: <input type="number" step="0.01" name="price" required />
    Category: <input type="text" name="category" />
    <button type="submit">Add</button>
</form>

<h3>Existing Products</h3>
<table border="1">
    <tr><th>ID</th><th>Name</th><th>Price</th><th>Category</th><th></th></tr>
    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.price}</td>
            <td>${p.category}</td>
            <td>
                <form method="post" action="products">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="id" value="${p.id}" />
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>