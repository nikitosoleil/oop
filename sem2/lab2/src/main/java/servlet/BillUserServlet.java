package servlet;

import entity.Bill;
import entity.Service;
import service.BillService;
import service.ServiceService;
import util.BeanFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/bill")
public class BillUserServlet extends HttpServlet {
    private final BillService billService;
    private final ServiceService serviceService;

    public BillUserServlet() {
        super();
        billService = (BillService) BeanFactory.getBean(BillService.class);
        serviceService = (ServiceService) BeanFactory.getBean(ServiceService.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Date date = Date.valueOf(request.getParameter("date"));
        Long serviceId = Long.parseLong(request.getParameter("service"));
        Long id = Long.parseLong(request.getParameter("id"));

        billService.billUser(id, new Bill(
                null,
                date,
                serviceService.getServiceById(serviceId).getPrice()
        ));

        response.sendRedirect("/users");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/bill-user.jsp");
        request.setAttribute("id", id);
        dispatcher.forward(request, response);
    }
}
