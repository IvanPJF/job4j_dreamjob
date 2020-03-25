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
            var arr = [$('#login'), $('#password'), $('#country'), $('#city')];
            for (var i = 0; i < arr.length; i++) {
                if (arr[i].val() === '') {
                    alert('Enter ' + arr[i].attr('title'));
                    return false;
                }
            }
            return true;
        }

        function clearCities() {
            $('#city option:first ~ option').remove();
        }

        function citiesByCountry() {
            clearCities();
            var idCountry = $('#country').val();
            if (idCountry !== '') {
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/dreamjob/location',
                    data: {idCountry: idCountry}
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
        }
    </script>
    <title>create</title>
</head>
<body>
<c:set value="${pageContext.servletContext.contextPath}" var="root"></c:set>
<div class="container">
    <div>
        <form class="form-horizontal" action="${root}/" method="post" id="formCreate">
            <br>
            <h3 class="text-center">Create new user</h3>
            <br>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="name">Name:</label>
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="text" name="name" id="name"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="login">Login:</label>
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="text" name="login" id="login" title="login"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="email">Email:</label>
                <div class="col-sm-4">
                    <input class="form-control" type="email" name="email" id="email"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="password">Password:</label>
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="password" name="password" id="password"
                           title="password"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="role">Role:</label>
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="role" id="role">
                        <c:forEach items="${s_roles}" var="role">
                            <option value="${role.name}">${role.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="country">Country:</label>
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="idCountry" id="country" title="country"
                            onchange="citiesByCountry()">
                        <option value="">select country...</option>
                        <c:forEach items="${countries}" var="country">
                            <option value="${country.id}">${country.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-2" for="city">City:</label>
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="idCity" id="city" title="city">
                        <option value="">select city...</option>
                    </select>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-sm-2 col-sm-offset-4">
            <form action="${root}/" method="get">
                <input class="btn btn-default btn-block btn-sm" type="submit" value="cancel"/>
            </form>
        </div>
        <div class="col-sm-2">
            <button class="btn btn-primary btn-block btn-sm" type="submit"
                    name="action" value="add" form="formCreate" onclick="return validate()">save
            </button>
        </div>
    </div>
</div>
</body>
</html>