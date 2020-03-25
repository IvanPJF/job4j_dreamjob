<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Users</title>
</head>
<body>
<c:set value="${pageContext.servletContext.contextPath}" var="root"></c:set>
<div class="container">
    <br>
    <div class="row">
        <div class="col-sm-12 text-right">
            <form action="${root}/signout" method="post">
                <input type="submit" class="btn btn-danger btn-sm" value="Sign out"/>
                <p>
                    <small>
                        Login=[ <b>${s_user.login}</b> ]
                        Role=[ <b>${s_user.role.name}</b> ]
                    </small>
                </p>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12 text-right">
            <c:if test="${s_user.role.name == 'admin'}">
                <form action="${root}/create">
                    <button type="submit" class="btn btn-primary btn-sm">add new user</button>
                </form>
            </c:if>
        </div>
    </div>
    <div class="table-responsive">
        <table class="table">
            <tr>
                <th>Name</th>
                <th>Login</th>
                <th>Email</th>
                <th>Date create</th>
                <th>Role</th>
                <th>Country</th>
                <th>City</th>
                <th></th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.login}</td>
                    <td>${user.email}</td>
                    <td>${user.createDate}</td>
                    <td>${user.role.name}</td>
                    <td>${user.country.name}</td>
                    <td>${user.city.name}</td>
                    <td>
                        <form method="post">
                            <input type="hidden" name="id" value="${user.id}">
                            <c:if test="${s_user.login == user.login || s_user.role.name == 'admin'}">
                                <button formaction="${root}/edit" name="action" value="update"
                                        type="submit" class="btn btn-primary btn-sm">edit
                                </button>
                                <c:if test="${s_user.role.name == 'admin'}">
                                    <button formaction="${root}/" name="action" value="delete"
                                            type="submit" class="btn btn-danger btn-sm">delete
                                    </button>
                                </c:if>
                            </c:if>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
