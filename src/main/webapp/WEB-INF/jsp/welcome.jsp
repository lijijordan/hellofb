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
        <form method="POST" enctype="multipart/form-data" id="fileUploadForm">
            <input type="file" name="file"/>
            <input type="submit" value="upload" id="btnSubmit"/> <input type="button" value="clear" id="clear"/>
            </br>
        </form>
        <span style="color: darkslategrey">total:${unreadMessage}</span></br></br>
    </div>

    <table id="user1" class="display" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PASSWORD</th>
            <th>CREATE TIME</th>
            <th>TYPE</th>
            <th>IP</th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PASSWORD</th>
            <th>CREATE TIME</th>
            <th>TYPE</th>
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
            <th>TYPE</th>
            <th>IP</th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PASSWORD</th>
            <th>CREATE TIME</th>
            <th>TYPE</th>
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

    function createNewCheckboxt(name, id){
        var checkbox = document.createElement('input');
        checkbox.type= 'checkbox';
        checkbox.name = name;
        checkbox.id = id;
        return checkbox;
    }
    //form.appendChild(createNewCheckboxt('theName', 'theID'));

    $('#clear').click(function (e) {
        e.preventDefault();
        var info = $('#foo').val();
        $.ajax({
            type: "POST",
            url: '/user/clear',
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify({data: info}),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                location.reload();
            },
            failure: function (errMsg) {
                alert('save error!');
            }
        });
    });


    /*$('#submit').click(function (e) {
        $('#submit').hide();
        $('#foo').hide();
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
                alert('let us hack facebook!');
            },
            failure: function (errMsg) {
                alert('save error!');
            }
        });
    });*/

    $(document).ready(function () {
        // load ips
        /*$.ajax({
            type: "GET",
            url: '/user/ip/list',
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify({data: info}),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                log.info(data);
            },
            failure: function (errMsg) {
                alert('save error!');
            }
        });*/


        $("#btnSubmit").click(function (event) {
            event.preventDefault();
            fire_ajax_submit();
        });

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
                {"data": "type"},
                {"data": "ip"}
            ]
        });

        $('#user2').DataTable({
            "processing": true,
            "serverSide": true,
            "searching": false,
            "ajax": "/user/facebook/account/list",
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
                {"data": "type"},
                {"data": "ip"}
            ]
        });
    });

    function fire_ajax_submit() {

        // Get form
        var form = $('#fileUploadForm')[0];
        var data = new FormData(form);
        data.append("CustomField", "This is some extra data, testing");
        $("#btnSubmit").prop("disabled", true);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/user/api/upload",
            data: data,
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                $("#result").text(data);
                console.log("SUCCESS : ", data);
                location.reload();
            },
            error: function (e) {
                $("#result").text(e.responseText);
                console.log("ERROR : ", e);
                $("#btnSubmit").prop("disabled", false);

            }
        });
    }

</script>

</body>

</html>
