package servlet;

import service.BillService;
import util.BeanFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/pay")
public class PayBillServlet extends HttpServlet {
    private final BillService billService;

    public PayBillServlet() {
        super();
        billService = (BillService) BeanFactory.getBean(BillService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        billService.pay(id);

        response.sendRedirect("/bills");
    }
}
