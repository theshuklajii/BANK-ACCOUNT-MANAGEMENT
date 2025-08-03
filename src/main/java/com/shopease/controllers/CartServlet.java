package com.shopease.controllers;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        int productId = Integer.parseInt(req.getParameter("productId"));

        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        switch (action) {
            case "add":
                Product p = productDAO.findAll().stream().filter(pr -> pr.getId()==productId).findFirst().orElse(null);
                if (p != null) {
                    cart.add(new CartItem(p.getId(), p.getName(), 1, p.getPrice()));
                }
                break;
            case "remove":
                cart.removeIf(it -> it.getProductId() == productId);
                break;
        }
        session.setAttribute("cart", cart);
        resp.sendRedirect("cart");
    }
}