package mapper;

import entity.Bill;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillMapper {
    public static final String BILL_ID = "id";
    public static final String BILL_DATE = "date";
    public static final String BILL_PRICE = "price";

    public Bill map(ResultSet resultSet) throws SQLException {
        Bill bill = new Bill();

        bill.setId(resultSet.getLong(BILL_ID));
        bill.setDate(resultSet.getDate(BILL_DATE));
        bill.setPrice(resultSet.getFloat(BILL_PRICE));

        return bill;
    }
}
