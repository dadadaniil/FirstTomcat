<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contacts database</title>
</head>
<body>
<h1>Contacts database</h1>

<c:choose>
    <jsp:useBean id="users_list" scope="request" type="com.example.firsttomcat.model.User"/>
    <c:when test="${empty users_list}">
        <p>No contacts found in the session</p>
    </c:when>
    <c:otherwise>
                <p>Found ${fn:length(users_list)} contacts in the session</p>

        <table border="1">
            <tr>
                <th>Name</th>
                <th>Number</th>
            </tr>

            <c:forEach items="${users_list}" var="contact">
                <tr>
                    <td>${contact.username}</td>
                    <td>${contact.email}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<ul>
    <li><a href="login.jsp">Menu</a></li>
</ul>
</body>
