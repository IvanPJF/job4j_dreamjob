<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Login</title>
</head>
<body>
<c:if test="${error != ''}">
    <div style="background-color: tomato">
        <c:out value="${error}"/>
    </div>
</c:if>
<div class="container">
    <form action="${pageContext.servletContext.contextPath}/signin" method="post">
        <br>
        <h3 class="text-center">Please sign in</h3>
        <br>
        <div class="form-group row">
            <div class="col-sm-4 col-sm-offset-4">
                <label for="lgn">Login:</label>
                <input class="form-control" type="text" name="login" id="lgn"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-4 col-sm-offset-4">
                <label for="pswd">Password:</label>
                <input class="form-control" type="password" name="password" id="pswd"/>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-sm-offset-4">
                <input class="btn btn-primary btn-block" type="submit" value="Sign in"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>
