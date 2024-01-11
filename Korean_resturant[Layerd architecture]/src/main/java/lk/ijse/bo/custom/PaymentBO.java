package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean deletePayment(String p_id) throws SQLException;

    boolean addPayment(PaymentDto dto) throws SQLException;

    boolean updatePayment(PaymentDto paymentDto) throws SQLException;

    PaymentDto searchPayment(String code) throws SQLException;

    List<PaymentDto> getAllPayment() throws SQLException;

    boolean updatePaymentInPlacePayment(PaymentDto paymentDto) throws SQLException;

    boolean savePaymentInPlacePayament(PaymentDto dto) throws SQLException;

}
