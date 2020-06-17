package dao;

import entity.User;
import lombok.RequiredArgsConstructor;
import mapper.UserMapper;
import util.BlockedUserException;
import util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserDAO {
    private final JdbcConnection jdbcConnection;
    private final UserMapper userMapper;

    private static final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE email=? AND password=?";
    private static final String GET_USERS = "SELECT * FROM users WHERE role='USER'";

    private static final String UPDATE_BLOCK = "UPDATE users SET block=? WHERE id=?";

    private static final String INSERT_USER = "INSERT INTO users (email, password, name, surname, block,role) VALUES (?,?,?,?,?,?::role_type)";

    public void save(User user) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setBoolean(5, user.isBlock());
            statement.setString(6, user.getRole().toString());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public User findUserByLogin(String email, String password) throws BlockedUserException {
        Connection connection = jdbcConnection.getConnection();
        try {
            User user = null;
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user = userMapper.map(resultSet);
            }

            statement.close();

            if (user.isBlock())
                throw new BlockedUserException("Sorry you were blocked");

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public List<User> findAllUsers() {
        Connection connection = jdbcConnection.getConnection();
        try {
            List<User> users = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(GET_USERS);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(userMapper.map(resultSet));
            }

            statement.close();

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void updateUserBlock(Long userId, boolean block) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_BLOCK);
            statement.setBoolean(1, block);
            statement.setLong(2, userId);

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
