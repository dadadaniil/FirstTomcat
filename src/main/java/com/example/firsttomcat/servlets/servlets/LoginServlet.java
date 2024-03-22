package com.example.firsttomcat.servlets.servlets;

import com.example.firsttomcat.servlets.model.User;
import com.example.firsttomcat.servlets.service.SessionService;
import com.example.firsttomcat.servlets.service.UserService;
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
    private static final Logger logger = LogManager.getLogger(MultiplyServlet.class);
    private UserService userService = new UserService();
    private SessionService sessionService = new SessionService();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("LoginServlet doPost");

        response.setContentType("text/html");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.authenticate(email, password);

        if (user != null) {
            logger.info("User with email " + email + " logged in");
            request.setAttribute("successMessage", "Now you are logged in");
            request.getRequestDispatcher("pages/success.jsp").forward(request, response);
        } else {
            logger.info("Invalid credentials for email " + email);
            request.setAttribute("errorMessage", "Invalid credentials for email " + email);
            request.getRequestDispatcher("pages/error.jsp").forward(request, response);
        }
    }
}