<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h2>Register Page</h2>
<c:if test="${not empty errorMessage}">
    <div style="color: red;">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div style="color: green;">${successMessage}</div>
</c:if>

<form action="${pageContext.request.contextPath}/register-servlet" method="post">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required pattern="\w+" title="Username must only contain letters, numbers and underscores.">
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required pattern=".{8,}" title="Password must be at least 8 characters long.">
    </div>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
    <div>
        <input type="submit" value="submit">
    </div>
</form>
</body>
</html>