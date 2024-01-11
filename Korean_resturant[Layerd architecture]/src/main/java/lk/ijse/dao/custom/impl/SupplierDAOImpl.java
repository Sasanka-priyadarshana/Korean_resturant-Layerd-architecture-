package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SalaryDAO;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dto.SupplierDto;
import lk.ijse.entity.Suppliers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public List<Suppliers> getAll() throws SQLException {
        ResultSet resultSet =SQLUtil.execute("SELECT * FROM suppliers");


        List<Suppliers> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new Suppliers(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return dtoList;
    }

    @Override
    public boolean save(Suppliers dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO suppliers VALUES (?,?,?)",dto.getSup_id(),dto.getAddress(),dto.getUsername());

    }

    @Override
    public boolean update(Suppliers dto) throws SQLException {
        return SQLUtil.execute("UPDATE suppliers SET address =? ,username =? WHERE sup_id =?",dto.getSup_id(),dto.getAddress(),
                dto.getUsername());

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute( "DELETE FROM suppliers WHERE sup_id = ?",id);
    }

    @Override
    public Suppliers search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM suppliers WHERE sup_id = ?",id);

        Suppliers dto = null;

        if(resultSet.next()) {
            dto = new Suppliers(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            );
        }
        return dto;
    }

    @Override
    public String getLastId() throws SQLException {
        return null;
    }
}
