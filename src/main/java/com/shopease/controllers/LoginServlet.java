package com.shopease.controllers;

import com.shopease.dao.UserDAO;
import com.shopease.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass  = req.getParameter("password");

        User user = userDAO.findByEmail(email);
        if (user != null && user.getPassword().equals(pass)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("products");
        } else {
            req.setAttribute("error", "Invalid credentials");
            req.getRequestDispatcher("views/login.jsp").forward(req, resp);
        }
    }
}