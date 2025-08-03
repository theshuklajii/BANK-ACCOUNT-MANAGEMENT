package com.shopease.controllers;

import com.shopease.dao.OrderDAO;
import com.shopease.models.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private OrderDAO orderDAO;
    
    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            session.setAttribute("redirectURL", request.getRequestURL().toString());
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Get user's orders
        List<Order> orders = orderDAO.getOrdersByUserId(userId);
        
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/views/orders.jsp").forward(request, response);
    }
}