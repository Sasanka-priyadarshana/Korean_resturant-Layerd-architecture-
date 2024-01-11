package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SalaryDAO;
import lk.ijse.dto.SalaryDto;
import lk.ijse.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {

    @Override
    public List<Salary> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM salary");


        List<Salary> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new Salary(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return dtoList;
    }

    @Override
    public boolean save(Salary dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO salary VALUES (?,?,?,?)", dto.getS_id(), dto.getPayment(), dto.getDate(), dto.getE_id());
    }

    @Override
    public boolean update(Salary dto) throws SQLException {
        return SQLUtil.execute("UPDATE salary SET payment =? ,date =? ,e_id =? WHERE s_id =?", dto.getS_id(), dto.getPayment(), dto.getDate(), dto.getE_id());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM salary WHERE s_id = ?", id);
    }

    @Override
    public Salary search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM salary WHERE s_id = ?", id);

        Salary dto = null;

        if (resultSet.next()) {
            dto = new Salary(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            );
        }
        return dto;
    }

    @Override
    public String getLastId() throws SQLException {
        return null;
    }
}
