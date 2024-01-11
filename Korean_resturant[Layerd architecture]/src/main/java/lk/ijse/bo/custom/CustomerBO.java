package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {

    boolean deleteCustomer(String c_id) throws SQLException;

     List<CustomerDto> getAllCustomer() throws SQLException;


     boolean addCustomer(CustomerDto dto) throws SQLException;

     boolean updateCustomer(CustomerDto dto) throws SQLException;
     CustomerDto searchCustomer(String code) throws SQLException;
}
