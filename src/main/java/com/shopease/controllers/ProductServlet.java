package com.shopease.controllers;

import com.shopease.dao.ProductDAO;
import com.shopease.models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    
    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String search = request.getParameter("search");
        String category = request.getParameter("category");
        
        List<Product> products;
        List<String> categories = productDAO.getAllCategories();
        
        // Handle search and filtering
        if (search != null && !search.trim().isEmpty() && category != null && !category.trim().isEmpty() && !"all".equals(category)) {
            // Search within specific category
            products = productDAO.searchProductsByCategory(search.trim(), category);
            request.setAttribute("searchTerm", search.trim());
            request.setAttribute("selectedCategory", category);
        } else if (search != null && !search.trim().isEmpty()) {
            // Search all products
            products = productDAO.searchProducts(search.trim());
            request.setAttribute("searchTerm", search.trim());
        } else if (category != null && !category.trim().isEmpty() && !"all".equals(category)) {
            // Filter by category
            products = productDAO.getProductsByCategory(category);
            request.setAttribute("selectedCategory", category);
        } else {
            // Show all products
            products = productDAO.getAllProducts();
        }
        
        // Set attributes for JSP
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        
        // Forward to products page
        request.getRequestDispatcher("/views/products.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}