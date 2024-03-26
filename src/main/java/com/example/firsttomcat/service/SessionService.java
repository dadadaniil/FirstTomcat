package com.example.firsttomcat.service;

import jakarta.servlet.http.HttpServletRequest;

public interface SessionService {
    void createSession(HttpServletRequest request, String email);
}
