<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="My Orders - ShopEase" />
<%@ include file="common/header.jsp" %>

<div class="row">
    <div class="col-12">
        <h2><i class="fas fa-list-alt me-2"></i>My Orders</h2>
        <hr>
    </div>
</div>

<c:choose>
    <c:when test="${not empty orders}">
        <div class="row">
            <c:forEach var="order" items="${orders}">
                <div class="col-12 mb-4">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col-md-3">
                                    <strong>Order #${order.orderId}</strong>
                                </div>
                                <div class="col-md-3">
                                    <fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy"/>
                                </div>
                                <div class="col-md-3">
                                    <span class="badge ${order.status == 'Pending' ? 'bg-warning' : 
                                                       order.status == 'Shipped' ? 'bg-info' : 
                                                       order.status == 'Delivered' ? 'bg-success' : 'bg-danger'}">
                                        ${order.status}
                                    </span>
                                </div>
                                <div class="col-md-3 text-end">
                                    <strong><fmt:formatNumber value="${order.totalAmount}" type="currency"/></strong>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <strong>Shipping Address:</strong><br>
                                    ${order.shippingAddress}
                                </div>
                                <div class="col-md-6">
                                    <strong>Customer:</strong> ${order.customerName}<br>
                                    <c:if test="${not empty order.customerPhone}">
                                        <strong>Phone:</strong> ${order.customerPhone}
                                    </c:if>
                                </div>
                            </div>
                            
                            <h6>Order Items:</h6>
                            <div class="table-responsive">
                                <table class="table table-sm">
                                    <thead class="table-light">
                                        <tr>
                                            <th>Product</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th>Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="item" items="${order.orderItems}">
                                            <tr>
                                                <td>
                                                    <div class="d-flex align-items-center">
                                                        <img src="${item.product.imageUrl}" alt="${item.product.name}" 
                                                             class="me-2" style="width: 40px; height: 40px; object-fit: cover;">
                                                        <div>
                                                            <small class="fw-bold">${item.product.name}</small><br>
                                                            <small class="text-muted">${item.product.category}</small>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td><fmt:formatNumber value="${item.price}" type="currency"/></td>
                                                <td>${item.quantity}</td>
                                                <td><fmt:formatNumber value="${item.totalPrice}" type="currency"/></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="row">
            <div class="col-12">
                <div class="text-center py-5">
                    <i class="fas fa-shopping-bag fa-3x text-muted mb-3"></i>
                    <h4>No orders yet</h4>
                    <p class="text-muted">You haven't placed any orders yet. Start shopping to see your orders here.</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                        <i class="fas fa-shopping-bag me-1"></i>Start Shopping
                    </a>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="common/footer.jsp" %>