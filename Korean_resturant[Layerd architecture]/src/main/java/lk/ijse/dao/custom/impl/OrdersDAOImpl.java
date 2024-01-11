package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrdersDAO;
import lk.ijse.dto.OrdersDto;
import lk.ijse.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAOImpl implements OrdersDAO {
    @Override
    public List<Orders> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM orders");

        List<Orders> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getString(6)
            ));
        }
        return dtoList;
    }

    @Override
    public boolean save(Orders dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO orders VALUES (?,?,?,?,?,?)",dto.getO_id(),dto.getDate(),dto.getType(),dto.getStatus(),dto.getTotal(),dto.getC_id());

    }

    @Override
    public boolean update(Orders dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM orders WHERE o_id = ?",id);
    }

    @Override
    public Orders search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM orders WHERE o_id = ?",id);

        Orders dto = null;

        if(resultSet.next()) {
            dto = new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getString(6)
            );
        }
        return dto;
    }

    @Override
    public String getLastId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT o_id FROM orders ORDER BY o_id DESC LIMIT 1");

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public boolean updateOrderStatusToPaid(String id) throws SQLException {
        return SQLUtil.execute( "update orders set status = 'Paid' where o_id=?",id);
    }

    @Override
    public boolean updateOrderStatusToNotPaid(String id) throws SQLException {
        return SQLUtil.execute("update orders set status = 'Not Paid' where o_id=?",id);
    }
}
