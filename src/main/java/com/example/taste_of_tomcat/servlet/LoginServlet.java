package com.example.taste_of_tomcat.servlet;

import com.example.taste_of_tomcat.model.User;
import com.example.taste_of_tomcat.service.impl.SessionServiceImpl;
import com.example.taste_of_tomcat.service.impl.UserServiceImpl;
import com.example.taste_of_tomcat.util.LocaleResolver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    static final String SERVLET_NAME = "loginServlet";
    static final String SERVLET_VALUE = "/login-servlet";
    static final String CONTENT_TYPE = "text/html";
    static final String PARAM_EMAIL = "email";
    static final String PARAM_PASSWORD = "password";
    static final String ATTR_SUCCESS_MESSAGE = "successMessage";
    static final String ATTR_ERROR_MESSAGE = "errorMessage";
    static final String REDIRECT_PATH = "/account-servlet";
    static final String JSP_PAGE_PATH = "/pages/login.jsp";

    private static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final UserServiceImpl userServiceImpl = new UserServiceImpl();
    private final SessionServiceImpl sessionServiceImpl = new SessionServiceImpl();
    private final LocaleResolver localeResolver = new LocaleResolver();


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("LoginServlet doPost");

        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);

        User user = userServiceImpl.authenticate(email, password);

        if (user != null) {
            logger.info("User with email " + email + " logged in");
            request.getSession().setAttribute(ATTR_SUCCESS_MESSAGE, "Now you are logged in and can manage your account.");
            Locale locale = localeResolver.resolveLocale(request);
            request.getSession().setAttribute("locale", locale);
            response.sendRedirect(request.getContextPath() + REDIRECT_PATH);
            sessionServiceImpl.createSession(request, email);
        } else {
            logger.info("Invalid credentials for email " + email);
            request.setAttribute(ATTR_ERROR_MESSAGE, "Invalid credentials for email " + email);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        }
    }
}