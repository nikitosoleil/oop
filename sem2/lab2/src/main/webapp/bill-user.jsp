<%@ page import="entity.Service" %>
<%@ page import="java.util.List" %>
<%@ page import="service.ServiceService" %>
<%@ page import="util.BeanFactory" %>
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

<%
    Long billedUser = (Long) request.getAttribute("id");
    ServiceService serviceService = (ServiceService) BeanFactory.getBean(ServiceService.class);
    System.out.println(billedUser);
    List<Service> userServices = serviceService.getServiceByUser(billedUser);
%>

<form action="/bill" method="POST">

    <table>
        <tr>
            <td><label for="date">Date: </label></td>
            <td><input type="date" id="date" name="date"></td>
        </tr>
        <tr>
            <td><label for="service">Service: </label></td>
            <td>

                <select name="service" id="service">
                    <% for (Service service : userServices) {%>
                    <option value="<%=service.getId()%>"><%=service.getName()%> - $<%=service.getPrice()%>
                    </option>
                    <%}%>
                </select>

            </td>
        </tr>
        <input type="number" id="id" name="id" style="display: none;" value="<%=billedUser%>">
        <tr>
            <td><input type="submit" value="Add"></td>
            <td><input type="reset" value="Clear"></td>
        </tr>
    </table>

</form>

</body>
</html>
