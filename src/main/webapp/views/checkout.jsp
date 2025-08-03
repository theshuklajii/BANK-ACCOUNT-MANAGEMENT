<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="Checkout - ShopEase" />
<%@ include file="common/header.jsp" %>

<div class="row">
    <div class="col-12">
        <h2><i class="fas fa-credit-card me-2"></i>Checkout</h2>
        <hr>
    </div>
</div>

<div class="row">
    <div class="col-lg-8">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Shipping Information</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/checkout" method="post">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="customerName" class="form-label">Full Name *</label>
                            <input type="text" class="form-control" id="customerName" name="customerName" 
                                   value="${user.fullName}" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="customerPhone" class="form-label">Phone Number</label>
                            <input type="tel" class="form-control" id="customerPhone" name="customerPhone" 
                                   value="${user.phone}">
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="shippingAddress" class="form-label">Shipping Address *</label>
                        <textarea class="form-control" id="shippingAddress" name="shippingAddress" 
                                  rows="3" required>${user.address}</textarea>
                    </div>
                    
                    <div class="d-grid">
                        <button type="submit" class="btn btn-success btn-lg">
                            <i class="fas fa-check me-2"></i>Place Order
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <div class="col-lg-4">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Order Summary</h5>
            </div>
            <div class="card-body">
                <c:forEach var="item" items="${cartItems}">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <div class="d-flex align-items-center">
                            <img src="${item.product.imageUrl}" alt="${item.product.name}" 
                                 class="me-2" style="width: 40px; height: 40px; object-fit: cover;">
                            <div>
                                <h6 class="mb-0">${item.product.name}</h6>
                                <small class="text-muted">Qty: ${item.quantity}</small>
                            </div>
                        </div>
                        <span><fmt:formatNumber value="${item.totalPrice}" type="currency"/></span>
                    </div>
                </c:forEach>
                
                <hr>
                <div class="d-flex justify-content-between mb-2">
                    <span>Subtotal:</span>
                    <span><fmt:formatNumber value="${cartTotal}" type="currency"/></span>
                </div>
                <div class="d-flex justify-content-between mb-2">
                    <span>Shipping:</span>
                    <span class="text-success">Free</span>
                </div>
                <div class="d-flex justify-content-between mb-2">
                    <span>Tax:</span>
                    <span>Included</span>
                </div>
                <hr>
                <div class="d-flex justify-content-between">
                    <h5>Total:</h5>
                    <h5 class="text-primary"><fmt:formatNumber value="${cartTotal}" type="currency"/></h5>
                </div>
            </div>
        </div>
        
        <div class="card mt-3">
            <div class="card-body">
                <h6><i class="fas fa-shield-alt me-2"></i>Secure Checkout</h6>
                <p class="mb-0 small text-muted">Your payment information is encrypted and secure. We never store your payment details.</p>
            </div>
        </div>
    </div>
</div>

<%@ include file="common/footer.jsp" %>