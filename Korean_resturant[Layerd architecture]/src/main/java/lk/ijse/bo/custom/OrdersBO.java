package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.MenuItemDto;
import lk.ijse.dto.OrdersDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrdersBO extends SuperBO {
     boolean addOrders(OrdersDto dto) throws SQLException;

     boolean deleteOrders(String o_id) throws SQLException;

     boolean updateOrders(OrdersDto ordersDto) throws SQLException;

     List<OrdersDto> getAllOrders() throws SQLException;
     OrdersDto searchOrders(String code) throws SQLException ;

     String generateNextOrderId() throws SQLException ;

     boolean updateOrderStatusToPaid(String id) throws SQLException;

     boolean updateOrderStatusToNotPaid(String id) throws SQLException;

    List<CustomerDto> getAllCustomer() throws SQLException;

     boolean placeOrder(OrdersDto orderDto, ArrayList<CartTm> list) throws SQLException;

    MenuItemDto searchMenuItem(String code) throws SQLException;

}
