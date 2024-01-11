package lk.ijse.bo.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.OrderDetailBO;
import lk.ijse.bo.custom.OrdersBO;
import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.bo.custom.impl.OrderDetailBOImpl;
import lk.ijse.bo.custom.impl.OrdersBOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDetilsDAO;
import lk.ijse.dao.custom.OrdersDAO;
import lk.ijse.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.dao.custom.impl.OrdersDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrdersDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrdersDAO ordersModel = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);
    OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERDETILS);

    @Override
    public boolean placeOrder(OrdersDto dto, ArrayList<CartTm> list) throws SQLException {
        boolean result = false;
        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSaved = ordersModel.save(new Orders(dto.getO_id(),dto.getDate(),dto.getType(),dto.getStatus(),(int) dto.getTotal(),dto.getC_id()));

            if (isSaved) {

                boolean isAdded = orderDetailBO.saveOrderDetail(dto.getO_id(), list);

                if (isAdded) {
                    connection.commit();
                    result = true;
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }
}
