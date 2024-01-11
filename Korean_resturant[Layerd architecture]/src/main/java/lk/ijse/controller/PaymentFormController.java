package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dto.PaymentDto;
import lk.ijse.dto.tm.PaymentTm;
import lk.ijse.bo.custom.impl.PaymentBOImpl;
import lk.ijse.bo.custom.impl.PlacePaymentBOImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentFormController {
    public TextField txtp_ID;
    public TextField txtamount;
    public TextField txto_id;
    public TableColumn colp_id;
    public TableColumn colamount;
    public TableColumn colo_id;
    public TableView tblPayment;

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }

    private void loadAllItems() {

        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try {
            List<PaymentDto> list = paymentBO.getAllPayment();
            for (PaymentDto dto: list){
                obList.add(new PaymentTm(
                        dto.getP_id(),
                        dto.getAmount(),
                        dto.getO_id()
                ));
            }
            tblPayment.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colp_id.setCellValueFactory(new PropertyValueFactory<>("p_id"));
        colamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colo_id.setCellValueFactory(new PropertyValueFactory<>("o_id"));

    }



    public void AddOnAction(ActionEvent actionEvent) {
        boolean isPaymentValid = validatePayment();
        if (isPaymentValid) {
            String id = txtp_ID.getText();
            String amount = txtamount.getText();
            String o_id = txto_id.getText();


            PaymentDto dto = new PaymentDto(id, amount, o_id);

            try {
                boolean isSaved = paymentBO.savePaymentInPlacePayament(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, " payment Saved!", ButtonType.OK).show();
                    clearFields();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.ERROR, "payment Not Saved!", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid payment Details", ButtonType.OK).show();
        }
    }

    private boolean validatePayment() {

        String PaymentIdText = txtp_ID.getText();
        Pattern compile = Pattern.compile("[P][0-9]{3,}");
        Matcher matcher = compile.matcher(PaymentIdText);
        boolean matches = matcher.matches();

        if (!matches) {
            new Alert(Alert.AlertType.ERROR, "Invalid payment ID").show();
            return false;
        }
        return  true;
    }

    private void clearFields() {
        txtp_ID.setText("");
        txtamount.setText("");
        txto_id.setText("");
    }

    public void DeleteOnAction(ActionEvent actionEvent) {

        String id = txtp_ID.getText();

        try {
            boolean isDeleted = paymentBO.deletePayment(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment deleted!").show();
                clearFields();
                loadAllItems();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void UpdateOnAction(ActionEvent actionEvent) {

        String p_id = txtp_ID.getText();
        String amount= txtamount.getText();
        String o_id = txto_id.getText();

        try {
            boolean isUpdated = paymentBO.updatePaymentInPlacePayment(new PaymentDto(p_id, amount, o_id));

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment updated!").show();
                clearFields();
                loadAllItems();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void ClearOnAction(ActionEvent actionEvent) {

        clearFields();

}

    public void searchIdOnAction(ActionEvent actionEvent) {

        String code = txtp_ID.getText();

        try {
            PaymentDto dto = paymentBO.searchPayment(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Payment not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(PaymentDto dto) {

        txtp_ID.setText(dto.getP_id());
        txtamount.setText(dto.getAmount());
        txto_id.setText(dto.getO_id());
    }
}
