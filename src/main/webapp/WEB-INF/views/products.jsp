<%@ page contentType='text/html;charset=UTF-8' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
    <title>ShopEase - Products</title>
</head>
<body>
<h2>Products</h2>
<form method='get' action='products'>
    Search: <input type='text' name='search'/>
    Category: <input type='text' name='category'/>
    <input type='submit' value='Filter'/>
</form>
<table border='1'>
    <tr><th>Name</th><th>Description</th><th>Price</th><th>Category</th><th>Action</th></tr>
    <c:forEach var='p' items='${products}'>
        <tr>
            <td>${p.name}</td>
            <td>${p.description}</td>
            <td>${p.price}</td>
            <td>${p.category}</td>
            <td>
                <form action='cart' method='post'>
                    <input type='hidden' name='action' value='add'/>
                    <input type='hidden' name='productId' value='${p.id}'/>
                    <input type='submit' value='Add to Cart'/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<a href='cart'>View Cart</a>
</body>
</html>