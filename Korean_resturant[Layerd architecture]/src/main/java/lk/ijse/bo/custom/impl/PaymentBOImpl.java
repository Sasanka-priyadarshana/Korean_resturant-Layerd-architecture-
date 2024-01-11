package lk.ijse.bo.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.PlacePaymentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.PaymentDto;
import lk.ijse.entity.Employee;
import lk.ijse.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    PlacePaymentBO placePaymentBO = (PlacePaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEPAYMENT);

    @Override
    public boolean deletePayment(String p_id) throws SQLException {
        return paymentDAO.delete(p_id);

    }

    @Override
    public boolean addPayment(PaymentDto dto) throws SQLException {
        return paymentDAO.save(new Payment(dto.getP_id(),dto.getAmount(),dto.getO_id()));
    }

    @Override
    public boolean updatePayment(PaymentDto paymentDto) throws SQLException {
        return paymentDAO.update(new Payment(paymentDto.getP_id(),paymentDto.getAmount(),paymentDto.getO_id()));
    }

    @Override
    public PaymentDto searchPayment(String code) throws SQLException {
        Payment payment = paymentDAO.search(code);
      return   new PaymentDto(payment.getP_id(),payment.getAmount(),payment.getO_id());
    }

    @Override
    public List<PaymentDto> getAllPayment() throws SQLException {
        List<Payment> list = paymentDAO.getAll();
        List<PaymentDto> dtoList = new ArrayList<>();

        for (Payment payment : list) {
            dtoList.add(new PaymentDto(payment.getP_id(),payment.getAmount(),payment.getO_id()));
        }
        return dtoList;
    }

    @Override
    public boolean updatePaymentInPlacePayment(PaymentDto paymentDto) throws SQLException {
        return placePaymentBO.updatePayment(paymentDto);
    }

    @Override
    public boolean savePaymentInPlacePayament(PaymentDto dto) throws SQLException {
        return placePaymentBO.savePayment(dto);
    }
}

