package com.shopease.controllers;

import com.shopease.dao.OrderDAO;
import com.shopease.dao.ProductDAO;
import com.shopease.dao.UserDAO;
import com.shopease.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        userDAO = new UserDAO();
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
        
        // Get dashboard statistics
        int totalOrders = orderDAO.getTotalOrderCount();
        BigDecimal totalRevenue = orderDAO.getTotalRevenue();
        int totalProducts = productDAO.getAllProducts().size();
        int totalUsers = userDAO.getAllUsers().size();
        
        // Get recent orders
        var recentOrders = orderDAO.getAllOrders();
        if (recentOrders.size() > 10) {
            recentOrders = recentOrders.subList(0, 10); // Show only 10 recent orders
        }
        
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("recentOrders", recentOrders);
        
        request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
    }
}