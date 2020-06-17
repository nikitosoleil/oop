package mapper;

import entity.Role;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_ROLE = "role";
    public static final String USER_BLOCK = "block";

    public User map(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong(USER_ID));
        user.setEmail(resultSet.getString(USER_EMAIL));
        user.setPassword(resultSet.getString(USER_PASSWORD));
        user.setName(resultSet.getString(USER_NAME));
        user.setSurname(resultSet.getString(USER_SURNAME));
        user.setRole(Role.valueOf(resultSet.getString(USER_ROLE)));
        user.setBlock(resultSet.getBoolean(USER_BLOCK));

        return user;
    }
}
