package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO {
     boolean saveOrderDetail(String oId, ArrayList<CartTm> list) throws SQLException;

}
