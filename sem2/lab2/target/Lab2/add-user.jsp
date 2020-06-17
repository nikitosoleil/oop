<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add User</title>
    <style>
        <%@include file="styles.css"%>
    </style>
</head>

<%@include file="header.jsp" %>

<body>

<form action="/add_user" method="POST">

    <table>
        <tr>
            <td><label for="email">Email: </label></td>
            <td><input type="email" id="email" name="email"></td>
        </tr>
        <tr>
            <td><label for="password">Password: </label></td>
            <td><input type="password" id="password" name="password"></td>
        </tr>
        <tr>
            <td><label for="name">Name: </label></td>
            <td><input type="text" id="name" name="name"></td>
        </tr>
        <tr>
            <td><label for="surname">Surname: </label></td>
            <td><input type="text" id="surname" name="surname"></td>
        </tr>
        </tr>
        <tr>
            <td><input type="submit" value="Add"></td>
            <td><input type="reset" value="Clear"></td>
        </tr>
    </table>

</form>

</body>
</html>
