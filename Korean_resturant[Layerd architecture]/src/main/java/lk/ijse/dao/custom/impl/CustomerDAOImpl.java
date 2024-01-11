package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public List<Customer> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");

        List<Customer> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)
            ));
        }
        return dtoList;
    }

    @Override
    public boolean save(Customer dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO customer VALUES (?,?,?)",dto.getC_id(),dto.getName(),dto.getTel_number());

    }

    @Override
    public boolean update(Customer dto) throws SQLException {
        return SQLUtil.execute("UPDATE customer SET name =? ,tel_number =? WHERE c_id =?",dto.getC_id(),dto.getName(),dto.getTel_number());

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM customer WHERE c_id = ?",id);

    }

    @Override
    public Customer search(String id) throws SQLException {
        {
            ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE c_id = ?");

            Customer dto = null;

            if(resultSet.next()) {
                dto = new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                );
            }
            return dto;
        }
    }

    @Override
    public String getLastId() throws SQLException {
        return null;
    }
}
