<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="Admin Dashboard - ShopEase" />
<%@ include file="../common/header.jsp" %>

<div class="row">
    <div class="col-12">
        <h2><i class="fas fa-tachometer-alt me-2"></i>Admin Dashboard</h2>
        <hr>
    </div>
</div>

<!-- Statistics Cards -->
<div class="row mb-4">
    <div class="col-lg-3 col-md-6 mb-3">
        <div class="card bg-primary text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4 class="mb-0">${totalOrders}</h4>
                        <p class="mb-0">Total Orders</p>
                    </div>
                    <div class="align-self-center">
                        <i class="fas fa-shopping-cart fa-2x"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-lg-3 col-md-6 mb-3">
        <div class="card bg-success text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4 class="mb-0"><fmt:formatNumber value="${totalRevenue}" type="currency"/></h4>
                        <p class="mb-0">Total Revenue</p>
                    </div>
                    <div class="align-self-center">
                        <i class="fas fa-dollar-sign fa-2x"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-lg-3 col-md-6 mb-3">
        <div class="card bg-info text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4 class="mb-0">${totalProducts}</h4>
                        <p class="mb-0">Total Products</p>
                    </div>
                    <div class="align-self-center">
                        <i class="fas fa-box fa-2x"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-lg-3 col-md-6 mb-3">
        <div class="card bg-warning text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4 class="mb-0">${totalUsers}</h4>
                        <p class="mb-0">Total Users</p>
                    </div>
                    <div class="align-self-center">
                        <i class="fas fa-users fa-2x"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Quick Actions -->
<div class="row mb-4">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Quick Actions</h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-3 mb-2">
                        <a href="${pageContext.request.contextPath}/admin/products?action=add" class="btn btn-success w-100">
                            <i class="fas fa-plus me-1"></i>Add Product
                        </a>
                    </div>
                    <div class="col-md-3 mb-2">
                        <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-primary w-100">
                            <i class="fas fa-box me-1"></i>Manage Products
                        </a>
                    </div>
                    <div class="col-md-3 mb-2">
                        <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-info w-100">
                            <i class="fas fa-clipboard-list me-1"></i>Manage Orders
                        </a>
                    </div>
                    <div class="col-md-3 mb-2">
                        <a href="${pageContext.request.contextPath}/admin/orders?status=Pending" class="btn btn-warning w-100">
                            <i class="fas fa-clock me-1"></i>Pending Orders
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Recent Orders -->
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="mb-0">Recent Orders</h5>
                <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-sm btn-outline-primary">
                    View All Orders
                </a>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty recentOrders}">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>Order ID</th>
                                        <th>Customer</th>
                                        <th>Date</th>
                                        <th>Amount</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="order" items="${recentOrders}">
                                        <tr>
                                            <td>#${order.orderId}</td>
                                            <td>${order.customerName}</td>
                                            <td><fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy"/></td>
                                            <td><fmt:formatNumber value="${order.totalAmount}" type="currency"/></td>
                                            <td>
                                                <span class="badge ${order.status == 'Pending' ? 'bg-warning' : 
                                                                   order.status == 'Shipped' ? 'bg-info' : 
                                                                   order.status == 'Delivered' ? 'bg-success' : 'bg-danger'}">
                                                    ${order.status}
                                                </span>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/orders?action=view&id=${order.orderId}" 
                                                   class="btn btn-sm btn-outline-primary">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center py-4">
                            <i class="fas fa-inbox fa-2x text-muted mb-2"></i>
                            <p class="text-muted">No recent orders</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>