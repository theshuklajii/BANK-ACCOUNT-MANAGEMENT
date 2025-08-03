package com.shopease.controllers;

import com.shopease.dao.OrderDAO;
import com.shopease.models.Order;
import com.shopease.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {
    private OrderDAO orderDAO;
    
    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
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
        
        if ("view".equals(action)) {
            handleViewOrder(request, response);
        } else {
            // Show all orders
            String status = request.getParameter("status");
            List<Order> orders;
            
            if (status != null && !status.trim().isEmpty() && !"all".equals(status)) {
                orders = orderDAO.getOrdersByStatus(status);
                request.setAttribute("selectedStatus", status);
            } else {
                orders = orderDAO.getAllOrders();
            }
            
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/views/admin/orders.jsp").forward(request, response);
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
        
        if ("updateStatus".equals(action)) {
            handleUpdateOrderStatus(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/orders");
        }
    }
    
    private void handleViewOrder(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String orderIdParam = request.getParameter("id");
        
        if (orderIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/orders");
            return;
        }
        
        try {
            int orderId = Integer.parseInt(orderIdParam);
            Order order = orderDAO.getOrderById(orderId);
            
            if (order == null) {
                request.setAttribute("error", "Order not found.");
                response.sendRedirect(request.getContextPath() + "/admin/orders");
                return;
            }
            
            request.setAttribute("order", order);
            request.getRequestDispatcher("/views/admin/order-details.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/orders");
        }
    }
    
    private void handleUpdateOrderStatus(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String orderIdParam = request.getParameter("orderId");
        String newStatus = request.getParameter("status");
        
        if (orderIdParam == null || newStatus == null) {
            response.sendRedirect(request.getContextPath() + "/admin/orders");
            return;
        }
        
        try {
            int orderId = Integer.parseInt(orderIdParam);
            boolean success = orderDAO.updateOrderStatus(orderId, newStatus);
            
            if (success) {
                request.setAttribute("success", "Order status updated successfully!");
            } else {
                request.setAttribute("error", "Failed to update order status.");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid order ID.");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/orders");
    }
}