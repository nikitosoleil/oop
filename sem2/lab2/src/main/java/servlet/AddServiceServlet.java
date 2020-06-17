package servlet;

import entity.Service;
import service.ServiceService;
import util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add_service")
public class AddServiceServlet extends HttpServlet {
    private final ServiceService serviceService;

    public AddServiceServlet() {
        super();
        serviceService = (ServiceService) BeanFactory.getBean(ServiceService.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("service_name");
        String description = request.getParameter("description");
        Float price = Float.parseFloat(request.getParameter("price"));

        serviceService.addService(new Service(
                null,
                name,
                description,
                price
        ));

        response.sendRedirect("/services");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/add-service.jsp").forward(request, response);
    }
}
