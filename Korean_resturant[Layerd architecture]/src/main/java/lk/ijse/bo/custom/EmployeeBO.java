package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {

    boolean deleteEmployee(String e_id) throws SQLException;

     boolean updateEmployee(final EmployeeDto dto) throws SQLException;

     boolean addEmployee(EmployeeDto dto) throws SQLException;

     List<EmployeeDto> getAllEmployee() throws SQLException;

     EmployeeDto searchEmployee(String code) throws SQLException ;
    }
