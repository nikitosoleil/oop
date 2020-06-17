<%@ page import="entity.Bill" %>
<%@ page import="java.util.List" %>
<%@ page import="service.BillService" %>
<%@ page import="util.BeanFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bills</title>
    <style>
        <%@include file="styles.css"%>
    </style>
</head>

<%@include file="header.jsp" %>

<%
    if (request.getAttribute("got_param") == null) {
        if (userRole == null) {
            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/bills?id=" + userId);
        }
    } else {
        List<Bill> bills = (List<Bill>) request.getAttribute("bills");
        BillService billService = (BillService) BeanFactory.getBean(BillService.class);
        if (bills != null) {

%>

<table>
    <tr>
        <th>Date</th>
        <th>Price</th>
        <th>Action</th>
    </tr>

    <% for (Bill bill : bills) {%>

    <tr>
        <td><%=bill.getDate().toString()%></td>
        <td>$<%=bill.getPrice()%></td>
        <td><%if(!billService.isPaid(bill.getId())) {%><a href="/pay?id=<%=bill.getId()%>">PAY</a><%}%></td>
    </tr>

    <%}%>

</table>

<%}}%>

<body>
</body>
</html>
