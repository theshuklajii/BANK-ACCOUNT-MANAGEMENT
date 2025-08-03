<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Signup - ShopEase</title>
    <link rel="stylesheet" href="../css/styles.css" />
</head>
<body>
<h2>Signup</h2>
<form method="post" action="signup">
    <label>Full Name: <input type="text" name="fullName" required/></label><br/>
    <label>Email: <input type="email" name="email" required/></label><br/>
    <label>Password: <input type="password" name="password" required/></label><br/>
    <button type="submit">Create Account</button>
</form>
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>
<p>Already have an account? <a href="login.jsp">Login</a></p>
</body>
</html>