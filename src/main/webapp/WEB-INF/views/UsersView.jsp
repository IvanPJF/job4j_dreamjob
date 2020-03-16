<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<c:set value="${pageContext.servletContext.contextPath}" var="root"></c:set>
<table>
    <tr>
        <td>
            <form action="${root}/signout" method="post">
                <input type="submit" value="Sign out"/>
            </form>
        </td>
        <td>
            Login=[${s_user.login}] Role=[${s_user.role.name}]
        </td>
    </tr>
</table>
<table>
    <tr>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
        <th>Date create</th>
        <th>Role</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.name}</td>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.createDate}</td>
            <td>${user.role.name}</td>
            <td>
                <form method="post">
                    <input type="hidden" name="id" value="${user.id}">
                    <c:if test="${s_user.login == user.login || s_user.role.name == 'admin'}">
                        <button formaction="${root}/edit" name="action" value="update"
                                type="submit">edit
                        </button>
                        <c:if test="${s_user.role.name == 'admin'}">
                            <button formaction="${root}/" name="action" value="delete"
                                    type="submit">delete
                            </button>
                        </c:if>
                    </c:if>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${s_user.role.name == 'admin'}">
        <tr>
            <td>
                <form action="${root}/create">
                    <button type="submit">add</button>
                </form>
            </td>
        </tr>
    </c:if>
</table>
</body>
</html>
