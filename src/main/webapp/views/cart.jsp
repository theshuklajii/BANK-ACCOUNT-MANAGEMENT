<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shopease.models.CartItem" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    if (cart == null) cart = java.util.Collections.emptyList();
%>
<html>
<head>
    <title>Cart - ShopEase</title>
    <link rel="stylesheet" href="../css/styles.css" />
</head>
<body>
<h2>Your Cart</h2>
<c:choose>
    <c:when test="${cart.empty}">
        <p>Your cart is empty.</p>
    </c:when>
    <c:otherwise>
        <table border="1">
            <tr><th>Product</th><th>Quantity</th><th>Price</th><th>Subtotal</th><th></th></tr>
            <% BigDecimal total = java.math.BigDecimal.ZERO; %>
            <c:forEach var="item" items="${cart}">
                <tr>
                    <td>${item.productName}</td>
                    <td>${item.quantity}</td>
                    <td>${item.price}</td>
                    <td>${item.subTotal}</td>
                    <td>
                        <form method="post" action="cart">
                            <input type="hidden" name="action" value="remove" />
                            <input type="hidden" name="productId" value="${item.productId}" />
                            <button type="submit">Remove</button>
                        </form>
                    </td>
                </tr>
                <% total = total.add(item.getSubTotal()); %>
            </c:forEach>
        </table>
        <p>Total: <strong><%= total %></strong></p>
        <form method="post" action="checkout">
            <label>Shipping Address:<br>
                <textarea name="address" required></textarea>
            </label><br>
            <button type="submit">Place Order</button>
        </form>
    </c:otherwise>
</c:choose>
<p><a href="products">Continue Shopping</a></p>
</body>
</html>