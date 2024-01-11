package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.OrdersDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
     boolean placeOrder(OrdersDto dto, ArrayList<CartTm> list) throws SQLException;
}
