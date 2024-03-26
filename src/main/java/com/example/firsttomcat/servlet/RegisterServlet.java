package com.example.firsttomcat.servlet;

import com.example.firsttomcat.service.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "registerServlet", value = "/register-servlet")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private final UserServiceImpl userServiceImpl = new UserServiceImpl();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("RegisterServlet doPost");

        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (userServiceImpl.userExists(email)) {
            logger.info("User with email " + email + " already exists");
            request.setAttribute("errorMessage", "User with email " + email + " already exists");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
        } else {
            try {
                userServiceImpl.register(username, password, email);
                logger.info("User with email " + email + " registered");
                request.setAttribute("successMessage", "You have been successfully registered, now you can manage your account.");
                request.getRequestDispatcher("/pages/account.jsp").forward(request, response);
            } catch (MessagingException e) {
                logger.error("Error sending email", e);
                request.setAttribute("errorMessage", "Error sending email. Please try again.");
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            }
        }
    }
}