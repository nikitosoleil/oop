package servlet;

import service.BillService;
import util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bills")
public class BillsServlet extends HttpServlet {
    private final BillService billService;

    public BillsServlet() {
        super();
        billService = (BillService) BeanFactory.getBean(BillService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = request.getParameter("id") == null ? null : Long.parseLong(request.getParameter("id"));
        if (id == null) {
            request.getRequestDispatcher("/bills.jsp").forward(request, response);
            return;
        } else {
            request.setAttribute("got_param", "true");
            request.setAttribute("bills", billService.findBillsByUser(id));
        }

        request.getRequestDispatcher("/bills.jsp").forward(request, response);
    }
}
