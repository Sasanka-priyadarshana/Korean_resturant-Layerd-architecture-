package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean deleteCustomer(String c_id) throws SQLException {
        return customerDAO.delete(c_id);
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

    public boolean addCustomer(CustomerDto dto) throws SQLException {
        return customerDAO.save(new Customer(dto.getC_id(), dto.getName(), dto.getTel_number()));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException {

        return customerDAO.update(new Customer(dto.getC_id(), dto.getName(), dto.getTel_number()));

    }

    @Override
    public CustomerDto searchCustomer(String code) throws SQLException {
        Customer customer = customerDAO.search(code);
       return new CustomerDto(customer.getC_id(), customer.getName(), customer.getTel_number());
    }
}



