package com.example.firsttomcat.servlet;

import com.example.firsttomcat.model.User;
import com.example.firsttomcat.model.impl.DatabaseUtilImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "usersServlet", value = "/users-servlet")
public class UsersServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UsersServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
         DatabaseUtilImpl dbUtil = new DatabaseUtilImpl();
        List<User> users = dbUtil.receiveUsersList();

        logger.info("Fetched " + users.size() + " users from the database");
        request.getSession().setAttribute("users_list", users);

        response.sendRedirect(request.getContextPath() + "/pages/users.jsp");
    }
}