<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>edit</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/" method="post">
    <table>
        <input type="hidden" name="id" value="${user.id}"/>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name" value="${user.name}"/></td>
        </tr>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login" value="${user.login}"/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type="text" name="email" value="${user.email}"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="text" name="password" value="${user.password}"/></td>
        </tr>
        <tr>
            <td>Role:</td>
            <td>
                <select name="role">
                    <c:if test="${s_user.role.name == 'admin'}">
                        <c:forEach items="${s_roles}" var="role">
                            <option value="${role.name}">${role.name}</option>
                        </c:forEach>
                    </c:if>
                    <c:if test="${s_user.role.name != 'admin'}">
                        <option value="${s_user.role.name}">${s_user.role.name}</option>
                    </c:if>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <button name="action" value="update" type="submit">save</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
