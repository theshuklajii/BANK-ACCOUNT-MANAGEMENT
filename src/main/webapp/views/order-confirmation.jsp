<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="Order Confirmation - ShopEase" />
<%@ include file="common/header.jsp" %>

<div class="row justify-content-center">
    <div class="col-lg-8">
        <div class="text-center mb-4">
            <i class="fas fa-check-circle fa-3x text-success mb-3"></i>
            <h2 class="text-success">Order Placed Successfully!</h2>
            <p class="text-muted">Thank you for your order. We'll send you a confirmation email shortly.</p>
        </div>
        
        <div class="card">
            <div class="card-header bg-success text-white">
                <h5 class="mb-0">Order Details</h5>
            </div>
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <strong>Order ID:</strong> #${order.orderId}
                    </div>
                    <div class="col-md-6">
                        <strong>Order Date:</strong> <fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy HH:mm"/>
                    </div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-6">
                        <strong>Customer:</strong> ${order.customerName}
                    </div>
                    <div class="col-md-6">
                        <strong>Status:</strong> 
                        <span class="badge bg-warning">${order.status}</span>
                    </div>
                </div>
                
                <div class="mb-3">
                    <strong>Shipping Address:</strong><br>
                    ${order.shippingAddress}
                </div>
                
                <c:if test="${not empty order.customerPhone}">
                    <div class="mb-3">
                        <strong>Phone:</strong> ${order.customerPhone}
                    </div>
                </c:if>
            </div>
        </div>
        
        <div class="card mt-3">
            <div class="card-header">
                <h5 class="mb-0">Order Items</h5>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover mb-0">
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
                                                 class="me-3" style="width: 50px; height: 50px; object-fit: cover;">
                                            <div>
                                                <h6 class="mb-1">${item.product.name}</h6>
                                                <small class="text-muted">${item.product.category}</small>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="align-middle">
                                        <fmt:formatNumber value="${item.price}" type="currency"/>
                                    </td>
                                    <td class="align-middle">${item.quantity}</td>
                                    <td class="align-middle">
                                        <strong><fmt:formatNumber value="${item.totalPrice}" type="currency"/></strong>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot class="table-light">
                            <tr>
                                <th colspan="3">Total Amount:</th>
                                <th><fmt:formatNumber value="${order.totalAmount}" type="currency"/></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
        
        <div class="card mt-3">
            <div class="card-body">
                <h6><i class="fas fa-info-circle me-2"></i>What's Next?</h6>
                <ul class="list-unstyled mb-0">
                    <li><i class="fas fa-check text-success me-2"></i>You'll receive an email confirmation shortly</li>
                    <li><i class="fas fa-check text-success me-2"></i>Your order will be processed within 1-2 business days</li>
                    <li><i class="fas fa-check text-success me-2"></i>Estimated delivery: 3-5 business days</li>
                    <li><i class="fas fa-check text-success me-2"></i>Track your order status in "My Orders"</li>
                </ul>
            </div>
        </div>
        
        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/orders" class="btn btn-primary me-2">
                <i class="fas fa-list-alt me-1"></i>View My Orders
            </a>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary">
                <i class="fas fa-shopping-bag me-1"></i>Continue Shopping
            </a>
        </div>
    </div>
</div>

<%@ include file="common/footer.jsp" %>