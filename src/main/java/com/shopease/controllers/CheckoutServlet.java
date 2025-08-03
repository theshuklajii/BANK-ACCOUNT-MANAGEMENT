package com.shopease.controllers;

import com.shopease.dao.CartDAO;
import com.shopease.dao.OrderDAO;
import com.shopease.dao.ProductDAO;
import com.shopease.models.CartItem;
import com.shopease.models.Order;
import com.shopease.models.OrderItem;
import com.shopease.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private CartDAO cartDAO;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    
    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAO();
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        User user = (User) session.getAttribute("user");
        
        if (userId == null || user == null) {
            session.setAttribute("redirectURL", request.getRequestURL().toString());
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Get cart items
        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        
        if (cartItems.isEmpty()) {
            request.setAttribute("error", "Your cart is empty. Please add items before checkout.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        
        // Calculate total
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            total = total.add(item.getTotalPrice());
        }
        
        // Pre-fill form with user data
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", total);
        request.setAttribute("user", user);
        
        request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
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
        
        // Get form data
        String customerName = request.getParameter("customerName");
        String customerPhone = request.getParameter("customerPhone");
        String shippingAddress = request.getParameter("shippingAddress");
        
        // Validate input
        StringBuilder errors = new StringBuilder();
        
        if (customerName == null || customerName.trim().isEmpty()) {
            errors.append("Customer name is required. ");
        }
        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            errors.append("Shipping address is required. ");
        }
        
        if (errors.length() > 0) {
            request.setAttribute("error", errors.toString());
            doGet(request, response);
            return;
        }
        
        // Get cart items
        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        
        if (cartItems.isEmpty()) {
            request.setAttribute("error", "Your cart is empty. Please add items before checkout.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        
        // Calculate total and validate stock
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            // Check stock availability
            if (item.getProduct().getStockQuantity() < item.getQuantity()) {
                request.setAttribute("error", "Insufficient stock for product: " + item.getProduct().getName());
                doGet(request, response);
                return;
            }
            total = total.add(item.getTotalPrice());
        }
        
        try {
            // Create order
            Order order = new Order(
                userId,
                total,
                "Pending",
                shippingAddress.trim(),
                customerName.trim(),
                customerPhone != null ? customerPhone.trim() : ""
            );
            
            int orderId = orderDAO.createOrder(order);
            
            if (orderId > 0) {
                // Create order items and update stock
                boolean allItemsCreated = true;
                
                for (CartItem cartItem : cartItems) {
                    // Create order item
                    OrderItem orderItem = new OrderItem(
                        orderId,
                        cartItem.getProductId(),
                        cartItem.getQuantity(),
                        cartItem.getProduct().getPrice()
                    );
                    
                    boolean itemCreated = orderDAO.createOrderItem(orderItem);
                    if (!itemCreated) {
                        allItemsCreated = false;
                        break;
                    }
                    
                    // Update product stock
                    boolean stockUpdated = productDAO.decreaseStock(
                        cartItem.getProductId(), 
                        cartItem.getQuantity()
                    );
                    
                    if (!stockUpdated) {
                        allItemsCreated = false;
                        break;
                    }
                }
                
                if (allItemsCreated) {
                    // Clear cart after successful order
                    cartDAO.clearCart(userId);
                    
                    // Redirect to order confirmation
                    response.sendRedirect(request.getContextPath() + "/order-confirmation?orderId=" + orderId);
                } else {
                    request.setAttribute("error", "Failed to create order items. Please try again.");
                    doGet(request, response);
                }
            } else {
                request.setAttribute("error", "Failed to create order. Please try again.");
                doGet(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error processing order: " + e.getMessage());
            request.setAttribute("error", "An error occurred while processing your order. Please try again.");
            doGet(request, response);
        }
    }
}