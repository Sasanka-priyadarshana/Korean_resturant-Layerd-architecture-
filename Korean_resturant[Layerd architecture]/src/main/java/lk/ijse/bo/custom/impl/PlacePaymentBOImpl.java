package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PlacePaymentBO;
import lk.ijse.bo.custom.impl.OrdersBOImpl;
import lk.ijse.bo.custom.impl.PaymentBOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrdersDAO;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dao.custom.impl.OrdersDAOImpl;
import lk.ijse.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.PaymentDto;
import lk.ijse.entity.Payment;

import java.sql.Connection;
import java.sql.SQLException;

public class PlacePaymentBOImpl implements PlacePaymentBO {
    PaymentDAO paymentModel = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    OrdersDAO ordersModel = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);

    @Override
    public boolean savePayment(PaymentDto dto) throws SQLException {
        Connection connection = null;
        boolean result = false;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSaved = paymentModel.save(new Payment(dto.getP_id(),dto.getAmount(),dto.getO_id()));

            if (isSaved) {
                boolean updated = ordersModel.updateOrderStatusToPaid(dto.getO_id());

                if (updated) {
                    connection.commit();
                    result = true;
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    @Override
    public boolean deletePayment(String id) throws SQLException {
        Connection connection = null;
        boolean result = false;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            Payment payment = paymentModel.search(id);

            boolean isDeleted = paymentModel.delete(id);

            if (isDeleted) {

                boolean updated = ordersModel.updateOrderStatusToNotPaid(payment.getO_id());

                if (updated) {
                    connection.commit();
                    result = true;
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    @Override
    public boolean updatePayment(PaymentDto dto) throws SQLException {
        Connection connection = null;
        boolean result = false;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            Payment payment = paymentModel.search(dto.getP_id());

            boolean isUpdated = paymentModel.update(new Payment(dto.getP_id(),dto.getAmount(),dto.getO_id()));

            if (isUpdated) {
                boolean updated = ordersModel.updateOrderStatusToPaid(dto.getO_id());

                if (updated) {

                    boolean isRemoved = ordersModel.updateOrderStatusToNotPaid(payment.getO_id());

                    if (isRemoved) {
                        connection.commit();
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }
}
