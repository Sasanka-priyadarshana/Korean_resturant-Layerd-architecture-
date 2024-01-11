package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.OrderDetailBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDetilsDAO;
import lk.ijse.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.dto.OrderDetailDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {
    private OrderDetilsDAO orderDetilsDAO = (OrderDetilsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETILS);

    @Override
    public boolean saveOrderDetail(String oId, ArrayList<CartTm> list) throws SQLException {
        for (CartTm tm : list) {
            boolean affectedRows = orderDetilsDAO.save(new OrderDetails(oId, tm.getCode(), tm.getQty()));
            if (!affectedRows) {
                return false;
            }
        }

        return true;
    }
}
