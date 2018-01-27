<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <title>Hello Facebook</title>

    <link rel="stylesheet" type="text/css"
          href="webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>

    <!--
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
    <c:url value="/css/main.css" var="jstlCss"/>
    <link href="${jstlCss}" rel="stylesheet"/>
    <c:url value="/css/jquery.dataTables.min.css" var="datatableCss"/>
    <link href="${datatableCss}" rel="stylesheet"/>

</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Spring Boot</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#about">About</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">

    <div class="starter-template">
        <textarea id="foo"></textarea>

        <button class="paginate_button" id="submit">Submit</button>
        <span style="color: darkslategrey">${unreadMessage}</span>
    </div>

    <table id="user1" class="display" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PASSWORD</th>
            <th>CREATE TIME</th>
            <th>MESSAGE</th>
            <th>IP</th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PASSWORD</th>
            <th>CREATE TIME</th>
            <th>MESSAGE</th>
            <th>IP</th>
        </tr>
        </tfoot>
    </table>

    <table id="user2" class="display" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PASSWORD</th>
            <th>CREATE TIME</th>
            <th>MESSAGE</th>
            <th>IP</th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PASSWORD</th>
            <th>CREATE TIME</th>
            <th>MESSAGE</th>
            <th>IP</th>
        </tr>
        </tfoot>
    </table>
</div>


<!-- /.container -->

<script type="text/javascript"
        src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script src="https://momentjs.com/downloads/moment.js"></script>
<%--https://code.jquery.com/jquery-1.12.4.js
https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js--%>

<script type="text/javascript">

    $('#submit').click(function (e) {
        e.preventDefault();
        var info = $('#foo').val();
        $.ajax({
            type: "POST",
            url: '/user/row/add',
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify({data: info}),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                
            },
            failure: function (errMsg) {
                alert('save error!');
            }
        });
    });

    $(document).ready(function () {
        $('#user1').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": "/user/list",
            "searching": false,
            "columns": [
                {"data": "id"},
                {"data": "name"},
                {"data": "password"},
                {
                    data: "createTime",
                    render: function (d) {
                        return moment(d).format("YYYY/MM/DD HH:mm:ss");
                    }
                },
                {"data": "message"},
                {"data": "ip"}
            ]
        });

        $('#user2').DataTable({
            "processing": true,
            "serverSide": true,
            "searching": false,
            "ajax": "/user/identifyPhotosOfFriends/list",
            "columns": [
                {"data": "id"},
                {"data": "name"},
                {"data": "password"},
                {
                    data: "createTime",
                    render: function (d) {
                        return moment(d).format("YYYY/MM/DD HH:mm:ss");
                    }
                },
                {"data": "message"},
                {"data": "ip"}
            ]
        });
    });
</script>

</body>

</html>
