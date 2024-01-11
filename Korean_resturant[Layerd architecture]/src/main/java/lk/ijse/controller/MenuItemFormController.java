package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.MenuItemBO;
import lk.ijse.dto.MenuItemDto;
import lk.ijse.dto.tm.MenuItemTm;
import lk.ijse.bo.custom.impl.MenuItemBOImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuItemFormController {
    public TextField txtItemCode;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TableColumn colItemCode;
    public TableColumn colUnitPrice;
    public TableColumn colDescription;
    public TableColumn colName;
    public TableView tblManuItem;
    public TextField txtName;

        MenuItemBO menuItemBO = (MenuItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MENUITEM);

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }

    private void loadAllItems() {

        ObservableList<MenuItemTm> obList = FXCollections.observableArrayList();

        try {
            List<MenuItemDto> list = menuItemBO.getAllMenuItem();
            for (MenuItemDto dto: list){
                obList.add(new MenuItemTm(
                        dto.getItem_code(),
                        dto.getUnit_price(),
                        dto.getDescription(),
                        dto.getName()
                ));
            }
            tblManuItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("item_code"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

    }

    public void AddOnAction(ActionEvent actionEvent) {

        boolean isMenuItemValid = validateMenuItem();
        if (isMenuItemValid) {
            String item_code = txtItemCode.getText();
            String unit_price = txtUnitPrice.getText();
            String description =txtDescription.getText();
            String name = txtName.getText();

            MenuItemDto dto = new MenuItemDto(item_code ,unit_price ,description ,name);

            try {
                boolean isSaved = menuItemBO.addMenuItem(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, " MenuItem Saved!", ButtonType.OK).show();
                    clearFields();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.ERROR, "MenuItem Not Saved!", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid MenuItem Details", ButtonType.OK).show();
        }
    }

    private boolean validateMenuItem() {

        String MenuItemIdText = txtItemCode.getText();
        Pattern compile = Pattern.compile("[I][0-9]{3,}");
        Matcher matcher = compile.matcher(MenuItemIdText);
        boolean matches = matcher.matches();

        if (!matches) {
            new Alert(Alert.AlertType.ERROR, "Invalid Item Code").show();
            return false;
        }


        String nameText = txtName.getText();
        boolean isNameValid = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        if (!isNameValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid Menu Item Name").show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtItemCode.setText("");
        txtUnitPrice.setText("");
        txtDescription.setText("");
        txtName.setText("");
    }

    public void DeleteOnAction(ActionEvent actionEvent) {


        String code = txtItemCode.getText();

        try {
            boolean isDeleted = menuItemBO.deleteMenuItem(code);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "MenuItem deleted!").show();
                clearFields();
                loadAllItems();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void UpdateOnAction(ActionEvent actionEvent) {

        String item_code = txtItemCode.getText();
        String unit_price = txtUnitPrice.getText();
        String description = txtDescription.getText();
        String name = txtName.getText();

        try {
            boolean isUpdated = menuItemBO.updateMenuItem(new MenuItemDto(item_code ,unit_price ,description ,name ));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "MenuItem updated!").show();
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
        String code = txtItemCode.getText();

        try {
            MenuItemDto dto = menuItemBO.searchMenuItem(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "MenuItem not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        
    }

    private void setFields(MenuItemDto dto) {

        txtItemCode.setText(dto.getItem_code());
        txtUnitPrice.setText(dto.getUnit_price());
        txtDescription.setText(dto.getDescription());
        txtName.setText(dto.getName());
    }
}
