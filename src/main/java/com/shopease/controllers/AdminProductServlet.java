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

@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    private boolean isAdmin(HttpSession session) {
        User u = (User) session.getAttribute("user");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req.getSession())) { resp.sendError(403); return; }
        req.setAttribute("products", productDAO.findAll());
        req.getRequestDispatcher("/views/adminProducts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAdmin(req.getSession())) { resp.sendError(403); return; }
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            Product p = new Product();
            p.setName(req.getParameter("name"));
            p.setDescription(req.getParameter("description"));
            p.setPrice(new BigDecimal(req.getParameter("price")));
            p.setCategory(req.getParameter("category"));
            productDAO.create(p);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            productDAO.delete(id);
        }
        resp.sendRedirect("products");
    }
}