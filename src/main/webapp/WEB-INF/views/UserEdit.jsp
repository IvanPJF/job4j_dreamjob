<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function validate() {
            var arr = [$('#login'), $('#password')];
            for (var i = 0; i < arr.length; i++) {
                if (arr[i].val() === '') {
                    alert('Enter ' + arr[i].attr('title'));
                    return false;
                }
            }
            return true;
        }

        function clearCities() {
            $('#city > option').remove();
        }

        function citiesByCountry() {
            clearCities();
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/dreamjob/location',
                data: {idCountry: $('#country').val()},
            }).done(function (data) {
                var cities = JSON.parse(data);
                for (var i = 0; i < cities.length; i++) {
                    $('#city')
                        .append('<option value=' + cities[i].id + '>' + cities[i].name + '</option>');
                }
            }).fail(function (error) {
                alert(error);
            });
        }
    </script>
    <title>edit</title>
</head>
<body>
<c:set value="${pageContext.servletContext.contextPath}" var="root"></c:set>
<div class="container">
    <div>
        <form class="form-horizontal" action="${root}/" method="post" id="formEdit">
            <br>
            <h3 class="text-center">Change user data</h3>
            <br>
            <input type="hidden" name="id" value="${user.id}"/>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="name">Name:</label>
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="text" name="name" id="name" value="${user.name}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="login">Login:</label>
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="text" name="login" id="login"
                           value="${user.login}" title="login"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="email">Email:</label>
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="email" name="email" id="email" value="${user.email}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="password">Password:</label>
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="password" name="password" id="password"
                           value="${user.password}" title="password"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="role">Role:</label>
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="role" id="role">
                        <c:if test="${s_user.role.name == 'admin'}">
                            <c:forEach items="${s_roles}" var="role">
                                <option value="${role.name}">${role.name}</option>
                            </c:forEach>
                        </c:if>
                        <c:if test="${s_user.role.name != 'admin'}">
                            <option value="${s_user.role.name}">${s_user.role.name}</option>
                        </c:if>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="country">Country:</label>
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="idCountry" id="country" title="country"
                            onchange="citiesByCountry()">
                        <option value="${user.country.id}" selected>${user.country.name}</option>
                        <c:forEach items="${countries}" var="country">
                            <c:if test="${country.id != user.country.id}">
                                <option value="${country.id}">${country.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="country">City:</label>
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="idCity" id="city" title="city">
                        <option value="${user.city.id}" selected>${user.city.name}</option>
                        <c:forEach items="${cities}" var="city">
                            <c:if test="${city.id != user.city.id}">
                                <option value="${city.id}">${city.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-sm-2 col-sm-offset-4">
            <form action="${root}/" method="get">
                <input class="btn btn-default btn-block" type="submit" value="cancel"/>
            </form>
        </div>
        <div class="col-sm-2">
            <button class="btn btn-primary btn-block" type="submit"
                    name="action" value="update" form="formEdit" onclick="return validate()">save
            </button>
        </div>
    </div>
</div>
</body>
</html>
