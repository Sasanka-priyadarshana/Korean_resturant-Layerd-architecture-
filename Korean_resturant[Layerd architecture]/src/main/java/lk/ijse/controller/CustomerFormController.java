package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.bo.custom.impl.CustomerBOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerFormController {
    public AnchorPane navigationPane;
    public TextField txtName;
    public TextField txtTelephoneNumber;
    public TextField txtCustomer_ID;
    public TableColumn colc_id;
    public TableColumn colname;
    public TableColumn colnumber;
    public TableView tblcustomer;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {
        setCellValueFactory();
        loadAllItems();
    }

    public void OrdersOnAction(ActionEvent actionEvent) throws IOException {
        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/orders_form.fxml")));
    }

    public void AddOnAction(ActionEvent actionEvent) {
        boolean isCustomerValid = validateCustomer();
        if (isCustomerValid) {
            String id = txtCustomer_ID.getText();
            String name = txtName.getText();
            int number = Integer.parseInt(txtTelephoneNumber.getText());

            CustomerDto dto = new CustomerDto(id, name, number);

            try {
                CustomerBOImpl customerModel = new CustomerBOImpl();
                boolean isSaved = customerModel.addCustomer(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, " Customer Saved!", ButtonType.OK).show();
                    clearFields();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer Not Saved!", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Details", ButtonType.OK).show();
        }
    }

    private void setCellValueFactory() {
        colc_id.setCellValueFactory(new PropertyValueFactory<>("c_id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colnumber.setCellValueFactory(new PropertyValueFactory<>("tel_number"));

    }

    private boolean validateCustomer() {
        String customerIdText = txtCustomer_ID.getText();
        Pattern compile = Pattern.compile("[C][0-9]{3,}");
        Matcher matcher = compile.matcher(customerIdText);
        boolean matches = matcher.matches();

        if (!matches) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID").show();
            return false;
        }
        String nameText = txtName.getText();
        boolean isNameValid = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        if (!isNameValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Name").show();
            return false;
        }
        String numberText = txtTelephoneNumber.getText();
        boolean isNumberValid = Pattern.compile("[(07)]\\d{9}|[+]\\d{11}").matcher(numberText).matches();
        if (!isNumberValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid Patient Number").show();
            return false;
        }
        return true;
    }

    private void loadAllItems() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> list = customerBO.getAllCustomer();
            for (CustomerDto dto : list) {
                obList.add(new CustomerTm(
                        dto.getC_id(),
                        dto.getName(),
                        dto.getTel_number()
                ));
            }
            tblcustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearFields() {
        txtCustomer_ID.setText("");
        txtName.setText("");
        txtTelephoneNumber.setText("");

    }


    public void DeleteOnAction(ActionEvent actionEvent) {

        String id = txtCustomer_ID.getText();

        try {
            boolean isDeleted = customerBO.deleteCustomer(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted!").show();
                clearFields();
                loadAllItems();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void UpdateONAction(ActionEvent actionEvent) {
        String id = txtCustomer_ID.getText();
        String name = txtName.getText();
        int number = Integer.parseInt(txtTelephoneNumber.getText());

        try {
            boolean isUpdated = customerBO.updateCustomer(new CustomerDto(id, name, number));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer updated!").show();
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
        String code = txtCustomer_ID.getText();

        try {
            CustomerDto dto = customerBO.searchCustomer(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(CustomerDto dto) {
        txtCustomer_ID.setText(dto.getC_id());
        txtName.setText(dto.getName());
        txtTelephoneNumber.setText(String.valueOf(dto.getTel_number()));
    }
}
