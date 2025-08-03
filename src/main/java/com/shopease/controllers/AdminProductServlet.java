package com.shopease.controllers;

import com.shopease.dao.ProductDAO;
import com.shopease.models.Product;
import com.shopease.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    
    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("edit".equals(action)) {
            handleEditProduct(request, response);
        } else if ("delete".equals(action)) {
            handleDeleteProduct(request, response);
        } else {
            // Show all products
            List<Product> products = productDAO.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/views/admin/products.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            handleAddProduct(request, response);
        } else if ("update".equals(action)) {
            handleUpdateProduct(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        }
    }
    
    private void handleEditProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String productIdParam = request.getParameter("id");
        
        if (productIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
            return;
        }
        
        try {
            int productId = Integer.parseInt(productIdParam);
            Product product = productDAO.getProductById(productId);
            
            if (product == null) {
                request.setAttribute("error", "Product not found.");
                response.sendRedirect(request.getContextPath() + "/admin/products");
                return;
            }
            
            List<String> categories = productDAO.getAllCategories();
            request.setAttribute("product", product);
            request.setAttribute("categories", categories);
            request.setAttribute("isEdit", true);
            request.getRequestDispatcher("/views/admin/product-form.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        }
    }
    
    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String productIdParam = request.getParameter("id");
        
        if (productIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
            return;
        }
        
        try {
            int productId = Integer.parseInt(productIdParam);
            boolean success = productDAO.deleteProduct(productId);
            
            if (success) {
                request.setAttribute("success", "Product deleted successfully!");
            } else {
                request.setAttribute("error", "Failed to delete product.");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID.");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
    
    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form data
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String category = request.getParameter("category");
        String stockStr = request.getParameter("stockQuantity");
        String imageUrl = request.getParameter("imageUrl");
        
        // Validate input
        StringBuilder errors = new StringBuilder();
        
        if (name == null || name.trim().isEmpty()) {
            errors.append("Product name is required. ");
        }
        if (description == null || description.trim().isEmpty()) {
            errors.append("Product description is required. ");
        }
        if (category == null || category.trim().isEmpty()) {
            errors.append("Product category is required. ");
        }
        
        BigDecimal price = null;
        int stockQuantity = 0;
        
        try {
            price = new BigDecimal(priceStr);
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                errors.append("Price must be greater than 0. ");
            }
        } catch (NumberFormatException e) {
            errors.append("Invalid price format. ");
        }
        
        try {
            stockQuantity = Integer.parseInt(stockStr);
            if (stockQuantity < 0) {
                errors.append("Stock quantity cannot be negative. ");
            }
        } catch (NumberFormatException e) {
            errors.append("Invalid stock quantity format. ");
        }
        
        if (errors.length() > 0) {
            request.setAttribute("error", errors.toString());
            request.setAttribute("name", name);
            request.setAttribute("description", description);
            request.setAttribute("price", priceStr);
            request.setAttribute("category", category);
            request.setAttribute("stockQuantity", stockStr);
            request.setAttribute("imageUrl", imageUrl);
            
            List<String> categories = productDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/views/admin/product-form.jsp").forward(request, response);
            return;
        }
        
        // Create product
        Product product = new Product(
            name.trim(),
            description.trim(),
            price,
            category.trim(),
            stockQuantity,
            imageUrl != null ? imageUrl.trim() : ""
        );
        
        boolean success = productDAO.createProduct(product);
        
        if (success) {
            request.setAttribute("success", "Product added successfully!");
        } else {
            request.setAttribute("error", "Failed to add product.");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
    
    private void handleUpdateProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String productIdStr = request.getParameter("productId");
        
        if (productIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
            return;
        }
        
        try {
            int productId = Integer.parseInt(productIdStr);
            
            // Get form data
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String category = request.getParameter("category");
            String stockStr = request.getParameter("stockQuantity");
            String imageUrl = request.getParameter("imageUrl");
            
            // Validate input (similar to add product)
            StringBuilder errors = new StringBuilder();
            
            if (name == null || name.trim().isEmpty()) {
                errors.append("Product name is required. ");
            }
            if (description == null || description.trim().isEmpty()) {
                errors.append("Product description is required. ");
            }
            if (category == null || category.trim().isEmpty()) {
                errors.append("Product category is required. ");
            }
            
            BigDecimal price = null;
            int stockQuantity = 0;
            
            try {
                price = new BigDecimal(priceStr);
                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    errors.append("Price must be greater than 0. ");
                }
            } catch (NumberFormatException e) {
                errors.append("Invalid price format. ");
            }
            
            try {
                stockQuantity = Integer.parseInt(stockStr);
                if (stockQuantity < 0) {
                    errors.append("Stock quantity cannot be negative. ");
                }
            } catch (NumberFormatException e) {
                errors.append("Invalid stock quantity format. ");
            }
            
            if (errors.length() > 0) {
                request.setAttribute("error", errors.toString());
                Product product = productDAO.getProductById(productId);
                request.setAttribute("product", product);
                request.setAttribute("isEdit", true);
                
                List<String> categories = productDAO.getAllCategories();
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/views/admin/product-form.jsp").forward(request, response);
                return;
            }
            
            // Update product
            Product product = new Product(
                productId,
                name.trim(),
                description.trim(),
                price,
                category.trim(),
                stockQuantity,
                imageUrl != null ? imageUrl.trim() : ""
            );
            
            boolean success = productDAO.updateProduct(product);
            
            if (success) {
                request.setAttribute("success", "Product updated successfully!");
            } else {
                request.setAttribute("error", "Failed to update product.");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID.");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}