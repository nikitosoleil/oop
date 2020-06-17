<%
    String userRole = null;
    Long userId = -1L;

    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++) {
            System.out.println(cookies[i].getName() + " - " + cookies[i].getValue());
            if (cookies[i].getName().equals("vf-user-role"))
                userRole = cookies[i].getValue();
            if (cookies[i].getName().equals("vf-user-id"))
                userId = Long.parseLong(cookies[i].getValue());
        }
    }
%>

<header>
    <p class="title">VodkaPhone</p>
    <% if (userRole != null) { %>
    <% if (userRole.equals("USER")) { %>
    <a href="/services">MY Services</a> - <a href="/bills">My Bills</a> - <a href="/logout">Log Out</a>
    <% } else if (userRole.equals("ADMIN")) {%>
    <a href="/users">Users</a> - <a href="/services">Services</a> - <a href="/logout">Log Out</a>
    <% } else { %>
    <a href="/home">Log In</a>
    <% } %>
    <% } %>
</header>
