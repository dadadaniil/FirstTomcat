package com.example.firsttomcat.servlet;

import com.example.firsttomcat.model.User;
import com.example.firsttomcat.service.impl.SessionServiceImpl;
import com.example.firsttomcat.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final UserServiceImpl userServiceImpl = new UserServiceImpl();
    private final SessionServiceImpl sessionServiceImpl = new SessionServiceImpl();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("LoginServlet doPost");

        response.setContentType("text/html");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userServiceImpl.authenticate(email, password);

        if (user != null) {
            logger.info("User with email " + email + " logged in");
            request.getSession().setAttribute("successMessage", "Now you are logged in and can manage your account.");
            response.sendRedirect(request.getContextPath() + "/account-servlet");
            sessionServiceImpl.createSession(request, email);
        } else {
            logger.info("Invalid credentials for email " + email);
            request.setAttribute("errorMessage", "Invalid credentials for email " + email);
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }
}