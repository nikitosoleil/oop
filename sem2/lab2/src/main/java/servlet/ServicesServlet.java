package servlet;

import service.ServiceService;
import util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/services")
public class ServicesServlet extends HttpServlet {
    private final ServiceService serviceService;

    public ServicesServlet() {
        super();
        serviceService = (ServiceService) BeanFactory.getBean(ServiceService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role");
        Long userId = request.getParameter("id") == null ? null : Long.parseLong(request.getParameter("id"));
        if (role == null) {
            request.getRequestDispatcher("/services.jsp").forward(request, response);
            return;
        } else if (role.equals("ADMIN") || role.equals("USER")) {
            request.setAttribute("got_param", "true");
            request.setAttribute("user-role", role);
            request.setAttribute("services", serviceService.getServices());
        } else {
            request.setAttribute("got_param", "true");
            request.setAttribute("user-role", role);
        }

        request.getRequestDispatcher("/services.jsp").forward(request, response);
    }
}
