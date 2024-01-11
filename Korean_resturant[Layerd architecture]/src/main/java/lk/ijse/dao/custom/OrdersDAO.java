package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.OrdersDto;
import lk.ijse.entity.Orders;

import java.sql.SQLException;

public interface OrdersDAO extends CrudDAO<Orders> {

    boolean updateOrderStatusToPaid(String id) throws SQLException;
    boolean updateOrderStatusToNotPaid(String id) throws SQLException;
}
