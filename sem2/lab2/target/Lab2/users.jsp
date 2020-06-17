<%@ page import="java.util.List" %>
<%@ page import="entity.User" %>
<%@ page import="entity.Bill" %>
<%@ page import="service.BillService" %>
<%@ page import="util.BeanFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
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
            response.sendRedirect("/users?role=" + userRole + "&id=" + userId);
        }
    } else {
        List<User> users = (List<User>) request.getAttribute("users");

        if (users != null && userRole.equals("ADMIN")) {

%>

<h1>Users <a href="/add_user">Add</a></h1>

<%
    for (User user : users) {
%>

<h3><%=user.getName()%> <%=user.getSurname()%> (<%=user.getEmail()%> - <%=user.getPassword()%>)
    <%if (user.isBlock()) {%><a href="/block?id=<%=user.getId()%>&action=unblock">UNBLOCK</a><%} else {%>
    <a href="/block?id=<%=user.getId()%>&action=block">BLOCK</a> <%}%>
    <a href="/bill?id=<%=user.getId()%>">Bill</a></h3>

<%
    BillService billService = (BillService) BeanFactory.getBean(BillService.class);
    List<Bill> bills = billService.findBillsByUser(user.getId());

    for (Bill bill : bills) {
%>

<p class="leftm"><%=bill.getId()%> - <%=bill.getDate()%> - $<%=bill.getPrice()%>
    - <%=(billService.isPaid(bill.getId()) ? "PAID" : "NOT PAID")%>
</p>

<%
                }
            }
        } else {
            response.sendRedirect("/error?message=\"Log In as Admin\"");
        }

    }
%>


</body>
</html>
