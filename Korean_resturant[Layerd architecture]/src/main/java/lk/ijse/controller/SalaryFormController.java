package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SalaryBO;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.tm.SalaryTm;
import lk.ijse.bo.custom.impl.SalaryBOImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SalaryFormController {
    public TextField txts_ID;
    public TextField txtPayment;
    public TextField txte_id;
    public DatePicker txtdate;
    public TableColumn cols_id;
    public TableColumn cole_id;
    public TableColumn coldate;
    public TableView tblsalary;
    public TableColumn colpayment;

    SalaryBO salaryBO = (SalaryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SALARY);

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }

    private void loadAllItems() {
        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

        try {
            List<SalaryDto> list = salaryBO.getAllSalary();
            for (SalaryDto dto: list){
                obList.add(new SalaryTm(
                        dto.getS_id(),
                        dto.getPayment(),
                        dto.getDate(),
                        dto.getE_id()
                ));
            }
            tblsalary.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        cols_id.setCellValueFactory(new PropertyValueFactory<>("s_id"));
        colpayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        cole_id.setCellValueFactory(new PropertyValueFactory<>("e_id"));

    }

    public void AddOnAction(ActionEvent actionEvent) {


        boolean isSalaryValid = validateSalary();
        if (isSalaryValid) {
            String id = txts_ID.getText();
            String payment =txtPayment.getText();
            String date = String.valueOf(txtdate.getValue());
            String e_id = txte_id.getText();

            SalaryDto dto = new SalaryDto(id, payment,date ,e_id);

            try {
                boolean isSaved = salaryBO.addSalary(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, " Salary Saved!", ButtonType.OK).show();
                    clearFields();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Salary Not Saved!", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Salary Details", ButtonType.OK).show();
        }
    }

    private boolean validateSalary() {
        String customerIdText = txts_ID.getText();
        Pattern compile = Pattern.compile("[S][0-9]{3,}");
        Matcher matcher = compile.matcher(customerIdText);
        boolean matches = matcher.matches();

        if (!matches) {
            new Alert(Alert.AlertType.ERROR, "Invalid Salary ID").show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        txts_ID.clear();
        txtPayment.clear();
        txte_id.clear();
        txtdate.setValue(null);
    }

    public void DeleteOnAction(ActionEvent actionEvent) {

        String id = txts_ID.getText();

        try {
            boolean isDeleted = salaryBO.deleteSalary(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Salary deleted!").show();
                clearFields();
                loadAllItems();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void UpdateOnAction(ActionEvent actionEvent) {

        String id = txts_ID.getText();
        String payment =txtPayment.getText();
        String date = String.valueOf(txtdate.getValue());
        String e_id = txte_id.getText();


        try {
            boolean isUpdated = salaryBO.updateSalary(new SalaryDto(id, payment ,date ,e_id));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Salary updated!").show();
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
        String code = txts_ID.getText();

        try {
            SalaryDto dto = salaryBO.searchSalary(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Salary not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(SalaryDto dto) {
        txts_ID.setText(dto.getS_id());
        txtPayment.setText(dto.getPayment());
        txtdate.setValue(LocalDate.parse(dto.getDate()));
        txte_id.setText(dto.getE_id());

    }
}
