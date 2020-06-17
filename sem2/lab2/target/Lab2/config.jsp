<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Please wait...</title>
</head>
<body>

<%
    Cookie userId = new Cookie("vf-user-id", request.getAttribute("user-id").toString());
    Cookie userRole = new Cookie("vf-user-role", request.getAttribute("user-role").toString());

    userId.setMaxAge(60*60*24);
    userRole.setMaxAge(60*60*24);

    response.addCookie(userId);
    response.addCookie(userRole);

    request.getRequestDispatcher("/services.jsp").forward(request, response);
%>

</body>
</html>
