package com.shopease.controllers;

import com.shopease.dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String kw = req.getParameter("keyword");
        String cat = req.getParameter("category");
        req.setAttribute("products", productDAO.search(kw, cat));
        req.getRequestDispatcher("views/products.jsp").forward(req, resp);
    }
}