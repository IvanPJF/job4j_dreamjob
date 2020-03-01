<%@ page import="ru.job4j.servlets.crud.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>edit</title>
</head>
<body>
<form action="list" method="post">
    <%User user = (User) request.getAttribute("id");%>
    <input type="hidden" name="id" value="<%=user.getId()%>"/>
    Name: <input type="text" name="name" value="<%=user.getName()%>"/><br>
    Login: <input type="text" name="login" value="<%=user.getLogin()%>"/><br>
    Email: <input type="text" name="email" value="<%=user.getEmail()%>"/><br>
    <button name="action" value="update" type="submit">save</button>
</form>
</body>
</html>
