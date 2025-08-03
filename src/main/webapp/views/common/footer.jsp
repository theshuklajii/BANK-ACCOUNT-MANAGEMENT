    </main>

    <footer class="footer">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h5><i class="fas fa-shopping-bag me-2"></i>ShopEase</h5>
                    <p class="mb-2">Your trusted online shopping destination</p>
                    <p class="text-muted">Quality products, great prices, fast delivery</p>
                </div>
                <div class="col-md-3">
                    <h6>Quick Links</h6>
                    <ul class="list-unstyled">
                        <li><a href="${pageContext.request.contextPath}/products" class="text-light text-decoration-none">Products</a></li>
                        <c:if test="${sessionScope.user != null && sessionScope.userRole == 'Customer'}">
                            <li><a href="${pageContext.request.contextPath}/cart" class="text-light text-decoration-none">Shopping Cart</a></li>
                            <li><a href="${pageContext.request.contextPath}/orders" class="text-light text-decoration-none">My Orders</a></li>
                        </c:if>
                    </ul>
                </div>
                <div class="col-md-3">
                    <h6>Contact Info</h6>
                    <p class="mb-1"><i class="fas fa-envelope me-2"></i>support@shopease.com</p>
                    <p class="mb-1"><i class="fas fa-phone me-2"></i>+1 (555) 123-4567</p>
                    <p class="mb-0"><i class="fas fa-map-marker-alt me-2"></i>123 Shopping St, Commerce City</p>
                </div>
            </div>
            <hr class="mt-4">
            <div class="row">
                <div class="col-12 text-center">
                    <p class="mb-0">&copy; 2024 ShopEase. All rights reserved.</p>
                </div>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Auto-hide alerts after 5 seconds
        setTimeout(function() {
            var alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                var bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
</body>
</html>