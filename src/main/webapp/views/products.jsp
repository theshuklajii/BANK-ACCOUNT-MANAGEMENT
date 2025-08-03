<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Products - ShopEase</title>
    <link rel="stylesheet" href="../css/styles.css" />
</head>
<body>
<h2>Product Catalogue</h2>
<form method="get" action="products">
    Search: <input type="text" name="keyword" value="${param.keyword}">
    Category: <input type="text" name="category" value="${param.category}">
    <button type="submit">Filter</button>
</form>
<table border="1">
    <tr><th>Name</th><th>Description</th><th>Price</th><th>Category</th><th></th></tr>
    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.name}</td>
            <td>${p.description}</td>
            <td>${p.price}</td>
            <td>${p.category}</td>
            <td>
                <form method="post" action="cart">
                    <input type="hidden" name="action" value="add" />
                    <input type="hidden" name="productId" value="${p.id}" />
                    <button type="submit">Add to Cart</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<p><a href="cart">View Cart</a></p>
</body>
</html>