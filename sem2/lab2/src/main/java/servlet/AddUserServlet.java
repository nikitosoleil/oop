package servlet;

import entity.Role;
import entity.Service;
import entity.User;
import service.UserService;
import util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add_user")
public class AddUserServlet extends HttpServlet {
    private final UserService userService;

    public AddUserServlet() {
        userService = (UserService) BeanFactory.getBean(UserService.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        userService.addUser(new User(
                null,
                email,
                password,
                name,
                surname,
                Role.USER,
                false
        ));

        response.sendRedirect("/users");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/add-user.jsp").forward(request, response);
    }
}
