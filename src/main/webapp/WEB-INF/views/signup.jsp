<%@ page contentType='text/html;charset=UTF-8' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
    <title>ShopEase - Signup</title>
</head>
<body>
<h2>Signup</h2>
<form action='auth' method='post'>
    <input type='hidden' name='action' value='signup'/>
    Name: <input type='text' name='name'/><br/>
    Email: <input type='text' name='email'/><br/>
    Password: <input type='password' name='password'/><br/>
    <input type='submit' value='Signup'/>
</form>
<c:if test='${not empty error}'>
    <p style='color:red'>${error}</p>
</c:if>
<a href='login.jsp'>Login</a>
</body>
</html>