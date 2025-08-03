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

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward to signup page
        request.getRequestDispatcher("/views/signup.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        
        // Validate input
        StringBuilder errors = new StringBuilder();
        
        if (username == null || username.trim().isEmpty()) {
            errors.append("Username is required. ");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.append("Email is required. ");
        }
        if (password == null || password.trim().isEmpty()) {
            errors.append("Password is required. ");
        }
        if (confirmPassword == null || !password.equals(confirmPassword)) {
            errors.append("Passwords do not match. ");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            errors.append("Full name is required. ");
        }
        
        // Validate password strength
        if (password != null && password.length() < 6) {
            errors.append("Password must be at least 6 characters long. ");
        }
        
        // Validate email format (basic validation)
        if (email != null && !email.contains("@")) {
            errors.append("Please enter a valid email address. ");
        }
        
        // Set default role if not specified
        if (role == null || role.trim().isEmpty()) {
            role = "Customer";
        }
        
        if (errors.length() > 0) {
            request.setAttribute("error", errors.toString());
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("address", address);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/views/signup.jsp").forward(request, response);
            return;
        }
        
        // Check if username or email already exists
        if (userDAO.isUsernameExists(username.trim())) {
            request.setAttribute("error", "Username already exists. Please choose a different username.");
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("address", address);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/views/signup.jsp").forward(request, response);
            return;
        }
        
        if (userDAO.isEmailExists(email.trim())) {
            request.setAttribute("error", "Email already exists. Please use a different email address.");
            request.setAttribute("username", username);
            request.setAttribute("fullName", fullName);
            request.setAttribute("address", address);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/views/signup.jsp").forward(request, response);
            return;
        }
        
        // Create new user
        User newUser = new User(
            username.trim(),
            email.trim(),
            password, // In production, this should be hashed
            fullName.trim(),
            role,
            address != null ? address.trim() : "",
            phone != null ? phone.trim() : ""
        );
        
        boolean success = userDAO.createUser(newUser);
        
        if (success) {
            // Registration successful - auto login
            User createdUser = userDAO.getUserByUsername(username.trim());
            if (createdUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", createdUser);
                session.setAttribute("userId", createdUser.getUserId());
                session.setAttribute("username", createdUser.getUsername());
                session.setAttribute("userRole", createdUser.getRole());
                
                // Redirect based on user role
                if (createdUser.isAdmin()) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/products");
                }
            } else {
                // Fallback to login page
                request.setAttribute("success", "Registration successful! Please login.");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("address", address);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/views/signup.jsp").forward(request, response);
        }
    }
}