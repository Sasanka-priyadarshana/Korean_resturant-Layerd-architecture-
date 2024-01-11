package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.PaymentDto;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlacePaymentBO  extends SuperBO {
     boolean savePayment(PaymentDto dto) throws SQLException;
     boolean deletePayment(String id) throws SQLException;

    boolean updatePayment(PaymentDto dto) throws SQLException;

}
