package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.StockItemBO;
import lk.ijse.dto.StockItemDto;
import lk.ijse.dto.tm.StockItemTm;
import lk.ijse.bo.custom.impl.StockItemBOImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockItemController {
    public TextField txtStocItem_Id;
    public TextField txtsname;
    public TextField txtqty;
    public TextField txtunitprice;
    public TableColumn colstockitem_id;
    public TableColumn colsname;
    public TableColumn colqty;
    public TableColumn colunitprice;
    public TableView tblStock;

    StockItemBO stockItemBO = (StockItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STOCKITEM);

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }

    private void loadAllItems() {
        ObservableList<StockItemTm> obList = FXCollections.observableArrayList();

        try {
            List<StockItemDto> list = stockItemBO.getAllStockItem();
            for (StockItemDto dto: list){
                obList.add(new StockItemTm(
                        dto.getStock_item_id(),
                        dto.getStock_name(),
                        dto.getQty(),
                        dto.getUnit_price()
                ));
            }
            tblStock.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colstockitem_id.setCellValueFactory(new PropertyValueFactory<>("stock_item_id"));
        colsname.setCellValueFactory(new PropertyValueFactory<>("stock_name"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));

    }

    public void AddOnAction(ActionEvent actionEvent) {

        boolean isStockItemValid = validateStockItem();
        if (isStockItemValid) {
            String id = txtStocItem_Id.getText();
            String name = txtsname.getText();
            String qty = txtqty.getText();
            String unit_price = txtunitprice.getText();

            StockItemDto dto = new StockItemDto(id, name, qty ,unit_price);

            try {
                boolean isSaved = stockItemBO.addStockItem(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, " Stock Item Saved!", ButtonType.OK).show();
                    clearFields();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Stock Item Not Saved!", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Stock Item Details", ButtonType.OK).show();
        }
    }

    private boolean validateStockItem() {
        String customerIdText = txtStocItem_Id.getText();
        Pattern compile = Pattern.compile("[S][0-9]{3,}");
        Matcher matcher = compile.matcher(customerIdText);
        boolean matches = matcher.matches();

        if (!matches) {
            new Alert(Alert.AlertType.ERROR, "Invalid Stock Item ID").show();
            return false;
        }
        String nameText = txtsname.getText();
        boolean isNameValid = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        if (!isNameValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid Stock Item Name").show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtStocItem_Id.setText("");
        txtsname.setText("");
        txtqty.setText("");
        txtunitprice.setText("");
    }

    public void ClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
        String id = txtStocItem_Id.getText();
        String name = txtsname.getText();
        String qty  = txtqty.getText();
        String unit_price = txtunitprice.getText();

        try {
            boolean isUpdated = stockItemBO.updateStockItem(new StockItemDto(id, name,qty ,unit_price ));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Stock Item updated!").show();
                clearFields();
                loadAllItems();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void DeleteOnAction(ActionEvent actionEvent) {

        String id = txtStocItem_Id.getText();

        try {
            boolean isDeleted = stockItemBO.deleteStockItem(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Stock Item deleted!").show();
                clearFields();
                loadAllItems();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void searchIdOnAction(ActionEvent actionEvent) {

        String code = colstockitem_id.getText();

        try {
            StockItemDto dto = stockItemBO.searchStockItem(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Stock item not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(StockItemDto dto) {
        txtStocItem_Id.setText(dto.getStock_item_id());
        txtsname.setText(dto.getStock_name());
        txtqty.setText(dto.getQty());
        txtunitprice.setText(dto.getUnit_price());
    }
}
