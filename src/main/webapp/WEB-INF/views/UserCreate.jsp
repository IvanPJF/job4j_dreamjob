<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>create</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/" method="post">
    <table>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name"/></td>
        </tr>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login"/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type="text" name="email"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="text" name="password"/></td>
        </tr>
        <tr>
            <td>Role:</td>
            <td>
                <select name="role">
                    <c:forEach items="${s_roles}" var="role">
                        <option value="${role.name}">${role.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <button name="action" value="add" type="submit">save</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>