package com.shopease.controllers;

import com.shopease.dao.UserDAO;
import com.shopease.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass  = req.getParameter("password");
        String name  = req.getParameter("fullName");

        User u = new User();
        u.setEmail(email);
        u.setPassword(pass);
        u.setFullName(name);
        u.setRole("CUSTOMER");

        if (userDAO.create(u)) {
            resp.sendRedirect("login.jsp");
        } else {
            req.setAttribute("error", "Email already exists");
            req.getRequestDispatcher("views/signup.jsp").forward(req, resp);
        }
    }
}