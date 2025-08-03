<%@ page contentType='text/html;charset=UTF-8' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
    <title>ShopEase - Login</title>
</head>
<body>
<h2>Login</h2>
<form action='auth' method='post'>
    <input type='hidden' name='action' value='login'/>
    Email: <input type='text' name='email'/><br/>
    Password: <input type='password' name='password'/><br/>
    <input type='submit' value='Login'/>
</form>
<c:if test='${not empty error}'>
    <p style='color:red'>${error}</p>
</c:if>
<a href='signup.jsp'>Signup</a>
</body>
</html>