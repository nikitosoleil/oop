<%@ page import="entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Homepage</title>
    <style>
        <%@include file="styles.css"%>
    </style>
</head>

<%@include file="header.jsp" %>

<body>

<script>
    function setCookie(cname, cvalue, exMins) {
        var d = new Date();
        d.setTime(d.getTime() + (exMins * 60 * 1000));
        var expires = "expires=" + d.toUTCString();
        document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
    }

    function clearCookies() {
        setCookie('vf-user-id', '', 0)
        setCookie('vf-user-role', '', 0)
    }
</script>


<h1>Login</h1>

<form action="/home" method="post">
    <table>
        <tr>
            <td><label for="email">Email</label></td>
            <td><input type="text" name="email" id="email"></td>
        </tr>
        <tr>
            <td><label for="password">Password</label></td>
            <td><input type="password" name="password" id="password"></td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Log In">
            </td>
            <td>
                <input type="button" id="rcook" value="Forget me please" onclick="clearCookies()">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
