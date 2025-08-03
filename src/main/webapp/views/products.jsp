<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="Products - ShopEase" />
<%@ include file="common/header.jsp" %>

<!-- Search and Filter Section -->
<div class="row mb-4">
    <div class="col-12">
        <div class="card">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/products" method="get" class="row g-3">
                    <div class="col-md-6">
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-search"></i></span>
                            <input type="text" class="form-control" name="search" 
                                   placeholder="Search products..." value="${searchTerm}">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <select class="form-select" name="category">
                            <option value="all">All Categories</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat}" ${selectedCategory == cat ? 'selected' : ''}>${cat}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100">
                            <i class="fas fa-search me-1"></i>Search
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Products Grid -->
<c:choose>
    <c:when test="${not empty products}">
        <div class="row">
            <c:forEach var="product" items="${products}">
                <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                    <div class="card product-card h-100">
                        <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}" 
                             style="height: 200px; object-fit: cover;">
                        <div class="card-body d-flex flex-column">
                            <h6 class="card-title">${product.name}</h6>
                            <p class="card-text text-muted small flex-grow-1">
                                ${product.description.length() > 80 ? 
                                  product.description.substring(0, 80) + '...' : product.description}
                            </p>
                            <div class="mt-auto">
                                <div class="d-flex justify-content-between align-items-center mb-2">
                                    <span class="h5 text-primary mb-0">
                                        <fmt:formatNumber value="${product.price}" type="currency"/>
                                    </span>
                                    <span class="badge bg-secondary">${product.category}</span>
                                </div>
                                <div class="d-flex justify-content-between align-items-center mb-2">
                                    <small class="text-muted">
                                        <i class="fas fa-box me-1"></i>
                                        ${product.stockQuantity} in stock
                                    </small>
                                    <c:if test="${product.stockQuantity == 0}">
                                        <span class="badge bg-danger">Out of Stock</span>
                                    </c:if>
                                </div>
                                
                                <c:if test="${sessionScope.user != null && sessionScope.userRole == 'Customer'}">
                                    <c:choose>
                                        <c:when test="${product.stockQuantity > 0}">
                                            <form action="${pageContext.request.contextPath}/cart" method="post" class="d-flex">
                                                <input type="hidden" name="action" value="add">
                                                <input type="hidden" name="productId" value="${product.productId}">
                                                <input type="number" name="quantity" value="1" min="1" 
                                                       max="${product.stockQuantity}" class="form-control form-control-sm me-2" 
                                                       style="width: 70px;">
                                                <button type="submit" class="btn btn-primary btn-sm flex-grow-1">
                                                    <i class="fas fa-cart-plus me-1"></i>Add to Cart
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-secondary btn-sm w-100" disabled>
                                                <i class="fas fa-times me-1"></i>Out of Stock
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <c:if test="${sessionScope.user == null}">
                                    <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-primary btn-sm w-100">
                                        <i class="fas fa-sign-in-alt me-1"></i>Login to Purchase
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <!-- Results Summary -->
        <div class="row mt-4">
            <div class="col-12">
                <div class="alert alert-info">
                    <i class="fas fa-info-circle me-2"></i>
                    Showing ${products.size()} product(s)
                    <c:if test="${not empty searchTerm}">
                        for search: "<strong>${searchTerm}</strong>"
                    </c:if>
                    <c:if test="${not empty selectedCategory}">
                        in category: "<strong>${selectedCategory}</strong>"
                    </c:if>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="row">
            <div class="col-12">
                <div class="text-center py-5">
                    <i class="fas fa-search fa-3x text-muted mb-3"></i>
                    <h4>No products found</h4>
                    <p class="text-muted">
                        <c:choose>
                            <c:when test="${not empty searchTerm}">
                                No products match your search criteria. Try adjusting your search terms.
                            </c:when>
                            <c:otherwise>
                                No products available at the moment.
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                        <i class="fas fa-arrow-left me-1"></i>View All Products
                    </a>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="common/footer.jsp" %>