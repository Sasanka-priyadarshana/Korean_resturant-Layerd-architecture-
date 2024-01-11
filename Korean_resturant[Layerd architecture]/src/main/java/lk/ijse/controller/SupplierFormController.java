package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.SupplierTm;
import lk.ijse.bo.custom.impl.SupplierBOImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierFormController {
    public TextField txtSupplier_id;
    public TextField txtAddress;
    public TextField txtu_id;
    public TableColumn colsuppliers;
    public TableColumn coladdress;
    public TableColumn colu_id;
    public TableView tblSupplier;
    public AnchorPane SupplierPane;


    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }

    private void setCellValueFactory() {
        colsuppliers.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colu_id.setCellValueFactory(new PropertyValueFactory<>("u_id"));
    }


    public void AddOnAction(ActionEvent actionEvent) {

        boolean isSupplierValid = validateSupplier();
        if (isSupplierValid) {
            String id = txtSupplier_id.getText();
            String address = txtAddress.getText();
            String u_id = txtu_id.getText();

            SupplierDto dto = new SupplierDto(id, address, u_id);

            try {
                boolean isSaved = supplierBO.addSupplier(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, " Suppliers Saved!", ButtonType.OK).show();
                    clearFields();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Suppliers Not Saved!", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Suppliers Details", ButtonType.OK).show();
        }
    }

    private void loadAllItems() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto > list  = supplierBO.getAllCustomer();
            for (SupplierDto dto: list){
                obList.add(new SupplierTm(
                        dto.getSup_id(),
                        dto.getAddress(),
                        dto.getU_id()
                ));
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearFields() {
        txtSupplier_id.setText("");
        txtAddress.setText("");
        txtu_id.setText("");

    }

    private boolean validateSupplier() {

        String employeesIdText = txtSupplier_id.getText();
        Pattern compile = Pattern.compile("[S][0-9]{3,}");
        Matcher matcher = compile.matcher(employeesIdText);
        boolean matches = matcher.matches();

        if (!matches) {
            new Alert(Alert.AlertType.ERROR, "Invalid Supplier ID").show();
            return false;
        }


        String addressText = txtAddress.getText();
        boolean isAddressValid = Pattern.compile("[A-Za-z]{3,}").matcher(addressText).matches();
        if (!isAddressValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid Supplier Address").show();
            return false;
        }
        return true;
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
        String id = txtSupplier_id.getText();

        try {
            boolean isDeleted = supplierBO.deleteCustomer(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted!").show();
                clearFields();
                loadAllItems();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
        String id = txtSupplier_id.getText();
        String address = txtAddress.getText();
        String u_id = txtu_id.getText();


        try {
            boolean isUpdated = supplierBO.updateSupplier(new SupplierDto(id, address,u_id ));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
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
        String code = txtSupplier_id.getText();

        try {
            SupplierDto dto = supplierBO.searchSupplier(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Supplier not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(SupplierDto dto) {
        txtSupplier_id.setText(dto.getSup_id());
        txtAddress.setText(dto.getAddress());
        txtu_id.setText(dto.getU_id());
    }

    public void StockitemOnAction(ActionEvent actionEvent) throws IOException {
        SupplierPane.getChildren().clear();
        SupplierPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/stock_item.fxml")));
    }
}
