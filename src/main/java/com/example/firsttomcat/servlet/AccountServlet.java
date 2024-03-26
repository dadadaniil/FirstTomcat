package com.example.firsttomcat.servlet;

import com.example.firsttomcat.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "accountServlet", value = "/account-servlet")
public class AccountServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AccountServlet.class);
    private final UserServiceImpl userServiceImpl = new UserServiceImpl();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("AccountServlet doPost");

        response.setContentType("text/html");

        String action = request.getParameter("action");
        String newUsername = request.getParameter("newUsername");
        String postContent = request.getParameter("postContent");

        String email = (String) request.getSession().getAttribute("email");

        switch (action) {
            case "Change Username":
                userServiceImpl.changeUsername(email, newUsername);
                request.setAttribute("successMessage", "Username has been successfully updated.");
                request.getRequestDispatcher("/pages/account.jsp").forward(request, response);
                break;
            case "Create Post":
                userServiceImpl.createPost(email, postContent);
                request.setAttribute("successMessage", "Post has been successfully created.");
                request.getRequestDispatcher("/pages/account.jsp").forward(request, response);
                break;
            case "Logout":
                request.getSession().invalidate();
                request.setAttribute("successMessage", "You have been successfully logged out.");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
                return;
            case "Delete Account":
                userServiceImpl.deleteAccount(email);
                request.setAttribute("successMessage", "Your account has been successfully deleted, you can create a new one");
                request.getSession().invalidate();
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
        }
    }
}