package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.MenuItemDto;
import lk.ijse.entity.Employee;
import lk.ijse.entity.MenuItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public boolean deleteEmployee(String e_id) throws SQLException {
        return employeeDAO.delete(e_id);
    }

    @Override
    public boolean updateEmployee(final EmployeeDto dto) throws SQLException {
        return employeeDAO.update(new Employee(dto.getE_id(),dto.getEmail(),dto.getAddress(),dto.getTel_number(),dto.getUsername()));
    }

    @Override
    public boolean addEmployee(EmployeeDto dto) throws SQLException {
        return employeeDAO.save(new Employee(dto.getE_id(),dto.getEmail(),dto.getAddress(),dto.getTel_number(),dto.getUsername()));
    }

    @Override
    public List<EmployeeDto> getAllEmployee() throws SQLException {
        List<Employee> list = employeeDAO.getAll();
        List<EmployeeDto> dtoList = new ArrayList<>();

        for (Employee employee : list) {
            dtoList.add(new EmployeeDto(employee.getE_id(),employee.getEmail(),employee.getAddress(),employee.getTel_number(),employee.getUsername()));
        }
        return dtoList;
    }

    @Override
    public EmployeeDto searchEmployee(String code) throws SQLException {

        Employee employee = employeeDAO.search(code);
       return new EmployeeDto(employee.getE_id(), employee.getEmail(), employee.getAddress(), employee.getTel_number(), employee.getUsername());
    }

}
