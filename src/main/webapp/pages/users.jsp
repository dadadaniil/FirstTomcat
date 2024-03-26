<%@ page import="com.example.firsttomcat.model.User" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users database</title>
</head>
<body>
<h1>Users database</h1>

<c:choose>
    <c:when test="${empty usersList}">
        <p>No users found in the session</p>
    </c:when>
    <c:otherwise>
        <p>Found ${fn:length(usersList)} users in the session</p>

        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
            </tr>
            <c:forEach items="${usersList}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td>${user.email}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
</body>