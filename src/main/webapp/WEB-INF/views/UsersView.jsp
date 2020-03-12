<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:set value="${pageContext.servletContext.contextPath}" var="root"></c:set>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.name}</td>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.createDate}</td>
            <td>
                <form method="post">
                    <input type="hidden" name="id" value="${user.id}">
                    <button formaction="${root}/edit" name="action" value="update"
                            type="submit">edit
                    </button>
                    <button formaction="${root}/" name="action" value="delete"
                            type="submit">delete
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <form action="${root}/create">
                <button type="submit">add</button>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
