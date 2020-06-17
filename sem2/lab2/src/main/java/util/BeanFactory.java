package util;

import dao.BillDAO;
import dao.ServiceDAO;
import dao.UserDAO;
import mapper.BillMapper;
import mapper.ServiceMapper;
import mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.BillService;
import service.ServiceService;
import service.UserService;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private static final Map<Class<?>, Object> beans = new HashMap<>();
    private static final Logger logger = LogManager.getRootLogger();

    static {
        JdbcConnection jdbcConnection = null;
        try {
            jdbcConnection = JdbcConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | FileNotFoundException e) {
            logger.error(e.getMessage());
        }

        BillDAO billDAO = new BillDAO(jdbcConnection, new BillMapper());
        ServiceDAO serviceDAO = new ServiceDAO(jdbcConnection, new ServiceMapper());
        UserDAO userDAO = new UserDAO(jdbcConnection, new UserMapper());
        beans.put(BillDAO.class, billDAO);
        beans.put(ServiceDAO.class, serviceDAO);
        beans.put(UserDAO.class, userDAO);

        BillService billService = new BillService(billDAO);
        ServiceService serviceService = new ServiceService(serviceDAO);
        UserService userService = new UserService(userDAO);
        beans.put(BillService.class, billService);
        beans.put(ServiceService.class, serviceService);
        beans.put(UserService.class, userService);
    }

    public static Object getBean(Class<?> beanClass) {
        return beans.get(beanClass);
    }
}
