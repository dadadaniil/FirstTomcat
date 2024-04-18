package com.example.firsttomcat.servlet;

import com.example.firsttomcat.model.User;
import com.example.firsttomcat.model.impl.DatabaseUtilImpl;
import com.example.firsttomcat.service.impl.UserServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

@WebServlet(name = "accountServlet", value = "/account-servlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class AccountServlet extends HttpServlet {
    private final UserServiceImpl userServiceImpl = new UserServiceImpl();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        String newUsername = request.getParameter("newUsername");
        String letterText = request.getParameter("letterToJoBiden");

        String email = (String) request.getSession().getAttribute("email");

        switch (action) {
            case "Change Username":
                userServiceImpl.changeUsername(email, newUsername);
                request.getSession().setAttribute("successMessage", "Username has been successfully updated.");
                response.sendRedirect(request.getContextPath() + "/account-servlet");
                return;
            case "Send letter":
                userServiceImpl.sendLetter(email, letterText);
                request.setAttribute("successMessage", "Letter has been successfully sent.");
                request.getRequestDispatcher("/pages/account.jsp").forward(request, response);
                return;
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
                return;
            case "Upload Avatar":
                Part filePart = request.getPart("avatar");
                userServiceImpl.uploadAvatar(filePart.getInputStream(), email);
                request.getSession().setAttribute("successMessage", "Avatar has been successfully uploaded.");
                response.sendRedirect(request.getContextPath() + "/account-servlet");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        String userEmail = (String) request.getSession().getAttribute("email");
        DatabaseUtilImpl databaseUtil = new DatabaseUtilImpl();
        User user = databaseUtil.findUserByEmail(userEmail);
        request.setAttribute("user", user);

        Optional<InputStream> optionalImageStream = databaseUtil.downloadFile(userEmail);

        if (optionalImageStream.isPresent()) {
            InputStream imageStream = optionalImageStream.get();
            byte[] imageBytes = IOUtils.toByteArray(imageStream);
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            request.setAttribute("userImage", base64Image);
        } else {
            ServletContext context = getServletContext();
            String fullPath = context.getRealPath("/images/avatar.jpg");
            Path path = Paths.get(fullPath);
            byte[] imageBytes = Files.readAllBytes(path);
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            request.setAttribute("userImage", base64Image);
        }

        request.getRequestDispatcher("/pages/account.jsp").forward(request, response);
//        request.getSession().removeAttribute("successMessage");
    }
}