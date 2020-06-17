package servlet;

import service.UserService;
import util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private final UserService userService;

    public UsersServlet() {
        super();
        userService = (UserService) BeanFactory.getBean(UserService.class);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role");
        if (role == null) {
            request.getRequestDispatcher("/users.jsp").forward(request, response);
            return;
        } else if (role.equals("ADMIN")) {
            request.setAttribute("got_param", "true");
            request.setAttribute("user-role", role);
            request.setAttribute("users", userService.findAllUser());
        } else {
            request.setAttribute("got_param", "true");
            request.setAttribute("user-role", role);
        }

        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
