<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login - ShopEase</title>
    <link rel="stylesheet" href="../css/styles.css" />
</head>
<body>
<h2>Login</h2>
<form method="post" action="login">
    <label>Email: <input type="email" name="email" required/></label><br/>
    <label>Password: <input type="password" name="password" required/></label><br/>
    <button type="submit">Login</button>
</form>
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>
<p>No account? <a href="signup.jsp">Signup</a></p>
</body>
</html>