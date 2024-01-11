package lk.ijse.bo.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.OrdersBO;
import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.MenuItemDAO;
import lk.ijse.dao.custom.OrdersDAO;
import lk.ijse.dao.custom.impl.OrdersDAOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.MenuItemDto;
import lk.ijse.dto.OrdersDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.entity.Customer;
import lk.ijse.entity.MenuItem;
import lk.ijse.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersBOImpl implements OrdersBO {

    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEORDER);

    MenuItemDAO menuItemDAO = (MenuItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MENUITEM);

    @Override
    public boolean addOrders(OrdersDto dto) throws SQLException {
        return ordersDAO.save(new Orders(dto.getO_id(),dto.getDate(),dto.getType(),dto.getStatus(),(int)dto.getTotal(),dto.getC_id()));
    }

    @Override
    public boolean deleteOrders(String o_id) throws SQLException {
        return ordersDAO.delete(o_id);
    }

    @Override
    public boolean updateOrders(OrdersDto ordersDto) throws SQLException {
        return ordersDAO.update(new Orders(ordersDto.getO_id(),ordersDto.getDate(),ordersDto.getType(),ordersDto.getStatus(),(int)ordersDto.getTotal(),ordersDto.getC_id()));
    }

    @Override
    public List<OrdersDto> getAllOrders() throws SQLException {
        List<Orders> list = ordersDAO.getAll();
        List<OrdersDto> dtoList = new ArrayList<>();

        for (Orders orders : list) {
            dtoList.add(new OrdersDto(orders.getO_id(),orders.getDate(),orders.getType(),orders.getStatus(),orders.getTotal(),orders.getC_id()));
        }
        return dtoList;
    }

    @Override
    public OrdersDto searchOrders(String code) throws SQLException {
        Orders orders = ordersDAO.search(code);
       return new OrdersDto(orders.getO_id(),orders.getDate(),orders.getType(),orders.getStatus(),orders.getTotal(),orders.getC_id());

    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return splitId(ordersDAO.getLastId());

    }

    private String splitId(String current) {
        if (current != null) {
            String[] split = current.split("O");
            int id = Integer.parseInt(split[1]);
            id++;
            if (9 >= id && id > 0) return "O00" + id;
            else if (99 >= id && id > 9) return "O0" + id;
            else if (id > 99) return String.valueOf(id);
        }
        return "O001";
    }

    @Override
    public boolean updateOrderStatusToPaid(String id) throws SQLException {
        return ordersDAO.updateOrderStatusToPaid(id);
    }

    @Override
    public boolean updateOrderStatusToNotPaid(String id) throws SQLException {
        return ordersDAO.updateOrderStatusToNotPaid(id);
    }

    @Override
    public List<CustomerDto> getAllCustomer() throws SQLException {
        List<Customer> list = customerDAO.getAll();
        List<CustomerDto> dtoList = new ArrayList<>();

        for (Customer customer : list) {
            dtoList.add(new CustomerDto(customer.getC_id(), customer.getName(), customer.getTel_number()));
        }
        return dtoList;
    }

    @Override
    public boolean placeOrder(OrdersDto orderDto, ArrayList<CartTm> list) throws SQLException {
        return placeOrderBO.placeOrder(orderDto,list);
    }

    @Override
    public MenuItemDto searchMenuItem(String code) throws SQLException {
        MenuItem menuItem = menuItemDAO.search(code);
        return new MenuItemDto(menuItem.getItem_code(), menuItem.getUnit_price(), menuItem.getDescription(), menuItem.getName());

    }
}


