<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Service</title>
    <style>
        <%@include file="styles.css"%>
    </style>
</head>

<%@include file="header.jsp" %>

<body>

<form action="/add_service" method="POST">

    <table>
        <tr>
            <td><label for="service_name">Name: </label></td>
            <td><input type="text" id="service_name" name="service_name"></td>
        </tr>
        <tr>
            <td><label for="description">Description: </label></td>
            <td><input type="text" id="description" name="description"></td>
        </tr>
        <tr>
            <td><label for="price">Price: </label></td>
            <td><input type="number" step="0.01" id="price" name="price"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Add"></td>
            <td><input type="reset" value="Clear"></td>
        </tr>
    </table>

</form>

</body>
</html>
