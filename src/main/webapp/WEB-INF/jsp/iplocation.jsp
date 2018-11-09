<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Movies - Group 6</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@include file="styling.jsp"%>
    <script src
                    ="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
    </script>
</head>

<body>
<%@include file="navbar.jsp"%>
<div class="container">
    <form id="ipForm" action="GeoIPTest" method="POST">
        <input type="text" name = "ipAddress" id = "ip"/>
        <input type="submit" name="submit" value="submit" />
    </form>
</div>
</body>



<script type="text/javascript">
    $(document).ready (function () {
        $.get("https://api.ipify.org?format=json",
            function (data) {
                $("#ip").val(data.ip);
            });
    });
    $('#ipForm').submit(function(event){
        event.preventDefault();
        $.ajax({
            url:"GeoIPTest",
            type:"POST",
            data: $.param({ipAddress: $("#ip").val()}),
            success: function(data){
                var obj = JSON.parse(data);
                alert(obj.city);

            }
        });
    });
</script>
</html>
