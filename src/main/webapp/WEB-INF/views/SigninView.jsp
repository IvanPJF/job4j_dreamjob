<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<c:if test="${error != ''}">
    <div style="background-color: tomato">
        <c:out value="${error}"/>
    </div>
</c:if>
<form action="${pageContext.servletContext.contextPath}/signin" method="post">
    <table>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="sign in"/></td>
        </tr>
    </table>
</form>
</body>
</html>
