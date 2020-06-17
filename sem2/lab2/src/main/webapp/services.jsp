<%@ page import="entity.Service" %>
<%@ page import="java.util.List" %>
<%@ page import="service.ServiceService" %>
<%@ page import="util.BeanFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Services</title>
    <style>
        <%@include file="styles.css"%>
    </style>
</head>

<%@include file="header.jsp" %>

<body>

<%
    if (request.getAttribute("got_param") == null) {
        if (userRole == null) {
            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/services?role=" + userRole + "&id=" + userId);
        }
    } else {
        List<Service> services = (List<Service>) request.getAttribute("services");

        if (services != null && userRole.equals("ADMIN") || userRole.equals("USER")) {

%>

<h1>Services <% if (userRole.equals("ADMIN")) {%> <a href="/add_service">Add</a><%}%></h1>

<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Actions</th>
    </tr>
    <%

        for (Service service : services) {

    %>

    <tr>

        <td><%=service.getId()%>
        </td>
        <td><%=service.getName()%>
        </td>
        <td><%=service.getDescription()%>
        </td>
        <td><%=service.getPrice()%>
        </td>
        <td>
            <% if (userRole.equals("USER")) {
                ServiceService serviceService = (ServiceService) BeanFactory.getBean(ServiceService.class);
                if (serviceService.isSubscribed(userId, service.getId())) {%>
            <a href="/service_action?action=unsub&userid=<%=userId%>&serviceid=<%=service.getId()%>">UNSUBSCRIBE</a>
            <% } else { %>
            <a href="/service_action?action=sub&userid=<%=userId%>&serviceid=<%=service.getId()%>">SUBSCRIBE</a>
            <% }
            }%>
        </td>

    </tr>

    <%

            }

        } else {
            response.sendRedirect("/error?message=\"Log In as User or Admin\"");
        }

    %>
</table>
<%

    }
%>


</body>
</html>
