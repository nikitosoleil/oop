package servlet;

import service.UserService;
import util.BeanFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/block")
public class BlockUserServlet extends HttpServlet {
    private final UserService userService;

    public BlockUserServlet() {
        super();
        userService = (UserService) BeanFactory.getBean(UserService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String action = request.getParameter("action");

        if (action.equals("block")) {
            userService.block(id);
        } else {
            userService.unblock(id);
        }

        response.sendRedirect("/users");
    }
}
