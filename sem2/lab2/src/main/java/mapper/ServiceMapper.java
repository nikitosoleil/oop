package mapper;

import entity.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceMapper {
    public static final String SERVICE_ID = "id";
    public static final String SERVICE_NAME = "name";
    public static final String SERVICE_DESCRIPTION = "description";
    public static final String SERVICE_PRICE = "price";

    public Service map(ResultSet resultSet) throws SQLException {
        Service service = new Service();

        service.setId(resultSet.getLong(SERVICE_ID));
        service.setName(resultSet.getString(SERVICE_NAME));
        service.setDescription(resultSet.getString(SERVICE_DESCRIPTION));
        service.setPrice(resultSet.getFloat(SERVICE_PRICE));

        return service;
    }
}
