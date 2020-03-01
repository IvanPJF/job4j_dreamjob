<%@ page import="ru.job4j.servlets.crud.model.User" %>
<%@ page import="ru.job4j.servlets.crud.logic.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<table>
    <tr>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
        <th>Date create</th>
    </tr>
    <% for (User user : ValidateService.getInstance().findAll()) { %>
    <tr>
        <td><%=user.getName()%></td>
        <td><%=user.getLogin()%></td>
        <td><%=user.getEmail()%></td>
        <td><%=user.getCreateDate()%></td>
        <td>
            <form>
                <input type="hidden" name="id" value="<%=user.getId()%>">
                <button formaction="edit" name="action" value="update" type="submit">edit</button>
                <button formaction="list" formmethod="post" name="action" value="delete" type="submit">delete</button>
            </form>
        </td>
    </tr>
    <% } %>
    <tr>
        <td>
            <form action="create">
                <button type="submit">add</button>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
