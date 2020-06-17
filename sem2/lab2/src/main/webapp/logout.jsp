<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logging Out...</title>
</head>
<body>


<script>
    function setCookie(cname, cvalue, exMins) {
        var d = new Date();
        d.setTime(d.getTime() + (exMins * 60 * 1000));
        var expires = "expires=" + d.toUTCString();
        document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";

        console.log('removed cookie ' + cname)
    }

    function clearCookies() {
        setCookie('vf-user-id', '', 0)
        setCookie('vf-user-role', '', 0)
    }

    clearCookies();
    window.location.href = '../home'
</script>


</body>
</html>
