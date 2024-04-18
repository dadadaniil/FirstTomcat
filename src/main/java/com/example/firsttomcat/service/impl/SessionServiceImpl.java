package com.example.firsttomcat.service.impl;

import com.example.firsttomcat.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionServiceImpl implements SessionService {
    @Override
    public void createSession(HttpServletRequest request, String email) {
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
    }
}