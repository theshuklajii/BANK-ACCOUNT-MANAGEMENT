package com.shopease.controllers;

import com.shopease.dao.OrderDAO;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (user == null || cart == null || cart.isEmpty()) {
            resp.sendRedirect("products");
            return;
        }

        String address = req.getParameter("address");
        BigDecimal total = cart.stream()
                .map(CartItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order(user.getId(), total, address);
        List<OrderItem> items = new ArrayList<>();
        cart.forEach(ci -> items.add(new OrderItem(ci.getProductId(), ci.getQuantity(), ci.getPrice())));

        try {
            orderDAO.createOrder(order, items);
            session.removeAttribute("cart"); // clear cart
            resp.sendRedirect("products?success=true");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}