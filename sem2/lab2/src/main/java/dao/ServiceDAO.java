package dao;

import entity.Service;
import lombok.RequiredArgsConstructor;
import mapper.ServiceMapper;
import util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ServiceDAO {
    private final JdbcConnection jdbcConnection;
    private final ServiceMapper serviceMapper;

    private static final String GET_SERVICES = "SELECT * FROM services";
    private static final String GET_SERVICE_BY_ID = "SELECT * FROM services WHERE id=?";
    private static final String GET_SERVICES_BY_USER = "SELECT * FROM services INNER JOIN user_service us on services.id = us.service_id WHERE user_id=?";
    private static final String CHECK_USER_SUBSCRIBED = "SELECT * FROM user_service WHERE user_id=? AND service_id=?";

    private static final String INSERT_USER_SERVICE = "INSERT INTO user_service (user_id, service_id) VALUES (?,?)";
    private static final String INSERT_SERVICE = "INSERT INTO services (name, description, price) VALUES (?,?,?)";

    private static final String DELETE_USER_SERVICE = "DELETE FROM user_service WHERE user_id=? AND service_id=?";

    public List<Service> findServices() {
        Connection connection = jdbcConnection.getConnection();
        List<Service> services = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_SERVICES);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                services.add(serviceMapper.map(resultSet));
            }

            statement.close();
            return services;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public List<Service> findServicesByUser(Long userId) {
        Connection connection = jdbcConnection.getConnection();
        List<Service> services = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_SERVICES_BY_USER);
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                services.add(serviceMapper.map(resultSet));
            }

            statement.close();
            return services;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public Service findServiceById(Long serviceId) {
        Connection connection = jdbcConnection.getConnection();
        try {
            Service service = null;
            PreparedStatement statement = connection.prepareStatement(GET_SERVICE_BY_ID);
            statement.setLong(1, serviceId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                service = serviceMapper.map(resultSet);
            }

            statement.close();
            return service;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public boolean isSubscribed(Long userId, Long serviceId) {
        Connection connection = jdbcConnection.getConnection();
        boolean subscribed = false;
        try {
            PreparedStatement statement = connection.prepareStatement(CHECK_USER_SUBSCRIBED);
            statement.setLong(1, userId);
            statement.setLong(2, serviceId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                subscribed = resultSet.getLong(1) == userId && resultSet.getLong(2) == serviceId;
            }

            statement.close();
            return subscribed;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void insertUserService(Long userId, Long serviceId) {
        Connection connection = jdbcConnection.getConnection();
        try {

            PreparedStatement statement = connection.prepareStatement(INSERT_USER_SERVICE);
            statement.setLong(1, userId);
            statement.setLong(2, serviceId);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void deleteUserService(Long userId, Long serviceId) {
        Connection connection = jdbcConnection.getConnection();
        try {

            PreparedStatement statement = connection.prepareStatement(DELETE_USER_SERVICE);
            statement.setLong(1, userId);
            statement.setLong(2, serviceId);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void save(Service service) {
        Connection connection = jdbcConnection.getConnection();
        try {

            PreparedStatement statement = connection.prepareStatement(INSERT_SERVICE);
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            statement.setFloat(3, service.getPrice());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

}
