package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDetilsDAO;
import lk.ijse.dto.OrderDetailDto;
import lk.ijse.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetilsDAO {
    @Override
    public List<OrderDetails> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(OrderDetails dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO order_details VALUES(?, ?, ?)", dto.getO_id(),dto.getItem_code(),dto.getQty());
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public OrderDetails search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getLastId() throws SQLException {
        return null;
    }
}
