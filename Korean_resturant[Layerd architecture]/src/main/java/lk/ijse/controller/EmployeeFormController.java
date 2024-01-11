package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.EmployeeTm;
import lk.ijse.bo.custom.impl.EmployeeBOImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public AnchorPane navigationPane;
    public TableView tblEmployee;
    public TableColumn colE_Id;
    public TableColumn colAddress;
    public TableColumn colnumber;
    public TextField txtEmployees_Id;
    public TextField txtEmail;
    public TextField txtnumber;
    public TextField txtAddress;
    public TableColumn colEmail;
    public TextField txtusername;
    public TableColumn colusername;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }

    private void loadAllItems() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> list = employeeBO.getAllEmployee();
            for (EmployeeDto dto: list){
                obList.add(new EmployeeTm(
                        dto.getE_id(),
                        dto.getEmail(),
                        dto.getAddress(),
                        dto.getTel_number(),
                        dto.getUsername()
                ));
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {

        colE_Id.setCellValueFactory(new PropertyValueFactory<>("e_id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colnumber.setCellValueFactory(new PropertyValueFactory<>("tel_number"));
        colusername.setCellValueFactory(new PropertyValueFactory<>("username"));


    }

    public void SalaryOnAction(ActionEvent actionEvent) throws IOException {

        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/Salary_form.fxml")));
    }

    public void AttendanceOnAction(ActionEvent actionEvent) throws IOException {

        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/attendance_form.fxml")));
    }
    

    public void AddOnAction(ActionEvent actionEvent) {

        boolean isEmployeeValid = validateEmployee();
        if (isEmployeeValid) {
            String id = txtEmployees_Id.getText();
            String email = txtEmail.getText();
            String address = txtAddress.getText();
            int number = Integer.parseInt(txtnumber.getText());
            String username =txtusername.getText();

            EmployeeDto dto = new EmployeeDto(id, email, address , number,username);

            try {
                boolean isSaved = employeeBO.addEmployee(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, " Employee Saved!", ButtonType.OK).show();
                    clearFields();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee Not Saved!", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee Details", ButtonType.OK).show();
        }

    }
    private void clearFields() {
        txtEmployees_Id.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtnumber.setText("");
        txtusername.setText("");

    }

    private boolean validateEmployee() {

       String EmployeesIdText = txtEmployees_Id.getText();
        Pattern compile = Pattern.compile("[E][0-9]{3,}");
        Matcher matcher = compile.matcher(EmployeesIdText);
        boolean matches = matcher.matches();

        if (!matches) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID").show();
            return false;
        }


        String addressText = txtAddress.getText();
        boolean isAddressValid = Pattern.compile("[A-Za-z]{3,}").matcher(addressText).matches();
        if (!isAddressValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee Address").show();
            return false;
        }

        String numberText = txtnumber.getText();
        boolean isNumberValid = Pattern.compile("[(07)]\\d{9}|[+]\\d{11}").matcher(numberText).matches();
        if (!isNumberValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee Number").show();
            return false;
        }
        return true;
    }



    public void DeleteOnAction(ActionEvent actionEvent) {

        String id = txtEmployees_Id.getText();

        try {
            boolean isDeleted = employeeBO.deleteEmployee(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
                clearFields();
                loadAllItems();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
        String id = txtEmployees_Id.getText();
        String basic= txtEmail.getText();
        String address = txtAddress.getText();
        int number = Integer.parseInt(txtnumber.getText());
        String uname = txtusername.getText();

        try {
            boolean isUpdated = employeeBO.updateEmployee(new EmployeeDto(id, basic, address , number,uname ));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
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
        String code = txtEmployees_Id.getText();

        try {
            EmployeeDto dto = employeeBO.searchEmployee(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(EmployeeDto dto) {
            txtEmployees_Id.setText(dto.getE_id());
            txtEmail.setText(dto.getEmail());
            txtAddress.setText(dto.getAddress());
            txtnumber.setText(String.valueOf(dto.getTel_number()));
            txtusername.setText(dto.getUsername());

    }
}
