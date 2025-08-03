package com.shopease.controllers;

import com.shopease.dao.CartDAO;
import com.shopease.dao.ProductDAO;
import com.shopease.models.CartItem;
import com.shopease.models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO;
    private ProductDAO productDAO;
    
    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAO();
        productDAO = new ProductDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            // User not logged in, redirect to login
            session.setAttribute("redirectURL", request.getRequestURL().toString());
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Get cart items for the user
        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        
        // Calculate total
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            total = total.add(item.getTotalPrice());
        }
        
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", total);
        request.setAttribute("itemCount", cartItems.size());
        
        request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            handleAddToCart(request, response, userId);
        } else if ("update".equals(action)) {
            handleUpdateCart(request, response, userId);
        } else if ("remove".equals(action)) {
            handleRemoveFromCart(request, response, userId);
        } else if ("clear".equals(action)) {
            handleClearCart(request, response, userId);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
    
    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            if (quantity <= 0) {
                request.setAttribute("error", "Quantity must be greater than 0");
                response.sendRedirect(request.getContextPath() + "/products");
                return;
            }
            
            // Check if product exists and has enough stock
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                request.setAttribute("error", "Product not found");
                response.sendRedirect(request.getContextPath() + "/products");
                return;
            }
            
            if (product.getStockQuantity() < quantity) {
                request.setAttribute("error", "Not enough stock available");
                response.sendRedirect(request.getContextPath() + "/products");
                return;
            }
            
            boolean success = cartDAO.addToCart(userId, productId, quantity);
            
            if (success) {
                request.setAttribute("success", "Product added to cart successfully!");
            } else {
                request.setAttribute("error", "Failed to add product to cart");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID or quantity");
        }
        
        response.sendRedirect(request.getContextPath() + "/cart");
    }
    
    private void handleUpdateCart(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        try {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            boolean success = cartDAO.updateCartItemQuantity(cartItemId, quantity);
            
            if (success) {
                request.setAttribute("success", "Cart updated successfully!");
            } else {
                request.setAttribute("error", "Failed to update cart");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid cart item ID or quantity");
        }
        
        response.sendRedirect(request.getContextPath() + "/cart");
    }
    
    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        try {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            
            boolean success = cartDAO.removeCartItem(cartItemId);
            
            if (success) {
                request.setAttribute("success", "Item removed from cart successfully!");
            } else {
                request.setAttribute("error", "Failed to remove item from cart");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid cart item ID");
        }
        
        response.sendRedirect(request.getContextPath() + "/cart");
    }
    
    private void handleClearCart(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        boolean success = cartDAO.clearCart(userId);
        
        if (success) {
            request.setAttribute("success", "Cart cleared successfully!");
        } else {
            request.setAttribute("error", "Failed to clear cart");
        }
        
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}