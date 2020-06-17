package servlet;

import service.ServiceService;
import util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/service_action")
public class ServiceActionServlet extends HttpServlet {
    private final ServiceService serviceService;

    public ServiceActionServlet() {
        serviceService = (ServiceService) BeanFactory.getBean(ServiceService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action"); // sub unsub
        Long userId = Long.parseLong(request.getParameter("userid"));
        Long serviceId = Long.parseLong(request.getParameter("serviceid"));

        switch (action) {
            case "sub":
                serviceService.subscribe(userId, serviceId);
                break;
            case "unsub":
                serviceService.unsubscribe(userId, serviceId);
                break;
        }

        response.sendRedirect("/services");
    }
}
