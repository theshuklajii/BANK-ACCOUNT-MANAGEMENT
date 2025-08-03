<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="Shopping Cart - ShopEase" />
<%@ include file="common/header.jsp" %>

<div class="row">
    <div class="col-12">
        <h2><i class="fas fa-shopping-cart me-2"></i>Shopping Cart</h2>
        <hr>
    </div>
</div>

<c:choose>
    <c:when test="${not empty cartItems}">
        <div class="row">
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Cart Items (${itemCount})</h5>
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
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${cartItems}">
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="${item.product.imageUrl}" alt="${item.product.name}" 
                                                         class="me-3" style="width: 60px; height: 60px; object-fit: cover;">
                                                    <div>
                                                        <h6 class="mb-1">${item.product.name}</h6>
                                                        <small class="text-muted">${item.product.category}</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="align-middle">
                                                <fmt:formatNumber value="${item.product.price}" type="currency"/>
                                            </td>
                                            <td class="align-middle">
                                                <form action="${pageContext.request.contextPath}/cart" method="post" 
                                                      class="d-flex align-items-center" style="width: 120px;">
                                                    <input type="hidden" name="action" value="update">
                                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                                    <input type="number" name="quantity" value="${item.quantity}" 
                                                           min="1" max="${item.product.stockQuantity}" 
                                                           class="form-control form-control-sm me-2" style="width: 70px;"
                                                           onchange="this.form.submit()">
                                                </form>
                                            </td>
                                            <td class="align-middle">
                                                <strong><fmt:formatNumber value="${item.totalPrice}" type="currency"/></strong>
                                            </td>
                                            <td class="align-middle">
                                                <form action="${pageContext.request.contextPath}/cart" method="post" 
                                                      style="display: inline;">
                                                    <input type="hidden" name="action" value="remove">
                                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                                    <button type="submit" class="btn btn-outline-danger btn-sm"
                                                            onclick="return confirm('Remove this item from cart?')">
                                                        <i class="fas fa-trash"></i>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="card-footer">
                        <div class="d-flex justify-content-between">
                            <form action="${pageContext.request.contextPath}/cart" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="clear">
                                <button type="submit" class="btn btn-outline-warning"
                                        onclick="return confirm('Clear all items from cart?')">
                                    <i class="fas fa-trash me-1"></i>Clear Cart
                                </button>
                            </form>
                            <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary">
                                <i class="fas fa-arrow-left me-1"></i>Continue Shopping
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="col-lg-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Order Summary</h5>
                    </div>
                    <div class="card-body">
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
                        <div class="d-flex justify-content-between mb-3">
                            <h5>Total:</h5>
                            <h5 class="text-primary"><fmt:formatNumber value="${cartTotal}" type="currency"/></h5>
                        </div>
                        
                        <div class="d-grid">
                            <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success btn-lg">
                                <i class="fas fa-credit-card me-2"></i>Proceed to Checkout
                            </a>
                        </div>
                        
                        <div class="mt-3 text-center">
                            <small class="text-muted">
                                <i class="fas fa-shield-alt me-1"></i>Secure checkout guaranteed
                            </small>
                        </div>
                    </div>
                </div>
                
                <!-- Shipping Info -->
                <div class="card mt-3">
                    <div class="card-body">
                        <h6><i class="fas fa-truck me-2"></i>Shipping Information</h6>
                        <ul class="list-unstyled mb-0">
                            <li><i class="fas fa-check text-success me-2"></i>Free shipping on all orders</li>
                            <li><i class="fas fa-check text-success me-2"></i>Delivery in 3-5 business days</li>
                            <li><i class="fas fa-check text-success me-2"></i>Easy returns within 30 days</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="row">
            <div class="col-12">
                <div class="text-center py-5">
                    <i class="fas fa-shopping-cart fa-3x text-muted mb-3"></i>
                    <h4>Your cart is empty</h4>
                    <p class="text-muted">Looks like you haven't added anything to your cart yet.</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                        <i class="fas fa-shopping-bag me-1"></i>Start Shopping
                    </a>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="common/footer.jsp" %>