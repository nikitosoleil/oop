package dao;

import entity.Bill;
import lombok.RequiredArgsConstructor;
import mapper.BillMapper;
import util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BillDAO {
    private final JdbcConnection jdbcConnection;
    private final BillMapper billMapper;

    private static final String GET_BILLS_BY_USER = "SELECT * FROM bills WHERE user_id=?";
    private static final String CHECK_BILL_PAID = "SELECT paid FROM bills WHERE id=?";

    private static final String UPDATE_PAID = "UPDATE bills SET paid = ? WHERE id=?";

    private static final String INSERT_BILL = "INSERT INTO bills (date, price, paid, user_id) VALUES (?,?,?,?)";


    public List<Bill> findBillsByUser(Long userId) {
        Connection connection = jdbcConnection.getConnection();
        List<Bill> bills = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BILLS_BY_USER);
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bills.add(billMapper.map(resultSet));
            }

            statement.close();
            return bills;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public boolean isPaid(Long billId) {
        Connection connection = jdbcConnection.getConnection();
        boolean paid = false;
        try {
            PreparedStatement statement = connection.prepareStatement(CHECK_BILL_PAID);
            statement.setLong(1, billId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                paid = resultSet.getBoolean(1);
            }

            statement.close();
            return paid;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void updateBillPay(boolean paid, Long billId) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PAID);
            statement.setBoolean(1, paid);
            statement.setLong(2, billId);

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void save(Long userId, Bill bill) {
        Connection connection = jdbcConnection.getConnection();
        try {

            PreparedStatement statement = connection.prepareStatement(INSERT_BILL);
            statement.setDate(1, bill.getDate());
            statement.setFloat(2, bill.getPrice());
            statement.setBoolean(3, false);
            statement.setLong(4, userId);

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
