package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<Employee> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employees");


        List<Employee> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)

            ));
        }
        return dtoList;
    }

    @Override
    public boolean save(Employee dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO employees VALUES (?,?,?,?,?)",dto.getE_id(),dto.getEmail(),dto.getAddress(),dto.getTel_number(),dto.getUsername());
    }

    @Override
    public boolean update(Employee dto) throws SQLException {
        return SQLUtil.execute("UPDATE employees SET tel_number =? ,email =? ,address =? ,username=? WHERE e_id =?",dto.getE_id(),dto.getEmail(),dto.getTel_number(),dto.getAddress(),dto.getUsername());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM employees WHERE e_id = ?",id);
    }

    @Override
    public Employee search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employees WHERE e_id = ?",id);

        Employee dto = null;

        if(resultSet.next()) {
            dto = new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );
        }
        return dto;
    }

    @Override
    public String getLastId() throws SQLException {
        return null;
    }
}
