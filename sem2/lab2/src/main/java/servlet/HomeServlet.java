package servlet;

import entity.User;
import service.UserService;
import util.BeanFactory;
import util.BlockedUserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final UserService userService;

    public HomeServlet() {
        super();
        userService = (UserService) BeanFactory.getBean(UserService.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user;

        try {
            user = userService.login(email, password);
            if (user == null)
                response.sendRedirect("/error?message=\"Invalid email or password!\"");
            else
                response.sendRedirect("/config?role=" + user.getRole() + "&id=" + user.getId());
        } catch (BlockedUserException e) {
            response.sendRedirect("/error?message=\"Sorry you were blocked!\"");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
