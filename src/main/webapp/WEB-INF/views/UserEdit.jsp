<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>edit</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/" method="post">
    <input type="hidden" name="id" value="${user.id}"/>
    Name: <input type="text" name="name" value="${user.name}"/><br>
    Login: <input type="text" name="login" value="${user.login}"/><br>
    Email: <input type="text" name="email" value="${user.email}"/><br>
    <button name="action" value="update" type="submit">save</button>
</form>
</body>
</html>
