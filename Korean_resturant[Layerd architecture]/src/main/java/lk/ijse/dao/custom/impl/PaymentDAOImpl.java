package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dto.PaymentDto;
import lk.ijse.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public List<Payment> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment");


        List<Payment> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            ));
        }
        return dtoList;
    }

    @Override
    public boolean save(Payment dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO payment VALUES (?,?,?)",dto.getP_id(),dto.getAmount(),dto.getO_id());

    }

    @Override
    public boolean update(Payment dto) throws SQLException {
        return SQLUtil.execute("UPDATE payment SET amount =? ,o_id =? WHERE p_id =?",dto.getP_id(),dto.getAmount(),dto.getO_id());

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM payment WHERE p_id = ?",id);

    }

    @Override
    public Payment search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment WHERE p_id = ?",id);

        Payment dto = null;

        if(resultSet.next()) {
            dto = new Payment(
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
