package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.OrdersBO;
import lk.ijse.dto.*;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.bo.custom.impl.CustomerBOImpl;
import lk.ijse.bo.custom.impl.MenuItemBOImpl;
import lk.ijse.bo.custom.impl.OrdersBOImpl;
import lk.ijse.bo.custom.impl.PlaceOrderBOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class OrdersFormController {
    private final ObservableList<CartTm> obList = FXCollections.observableArrayList();
    public AnchorPane navigationPane;
    public TextField txtType;
    public TableView tblOrders;
    public TextField txtOrderID;
    public TextField txtstatus;
    public TableColumn colstatus;
    public ComboBox<String> cmbitem_code;
    public ComboBox<String> cmbtype;
    public Label lblate;
    public TextField txtu_price;
    public TextField txtqty;
    public TextField txtamount;
    public ComboBox<String> cmbCustome_code;
    public Label lblTotal;
    public TableColumn col_code;
    public TableColumn colqty;
    public TableColumn colunitPrice;
    public TableColumn colamount;
    public TableColumn colAction;

    ArrayList<CartTm> list = new ArrayList<>();

    OrdersBO ordersBO = (OrdersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERS);


    public void initialize() {
        loadItemCodes();
        loadTime();
        setCellValueFactory();
        txtstatus.setText("Not Paid");
        lblTotal.setText("0000.00");
        loadCustomer();
        cmbtype.getItems().addAll("Set Menu", "Room", "Dining");
        setOrderId();
    }

    private void setOrderId() {
        try {
            String id = ordersBO.generateNextOrderId();
            txtOrderID.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<MenuItemDto> list = new MenuItemBOImpl().getAllMenuItem();

            for (MenuItemDto dto : list) {
                obList.add(dto.getItem_code());
            }
            cmbitem_code.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomer() {


        try {
            ObservableList<String> custIds = FXCollections.observableArrayList();
            List<CustomerDto> allCustomer = ordersBO.getAllCustomer();
            for (CustomerDto dto : allCustomer){
                custIds.add(dto.getC_id());
            }
            cmbCustome_code.setItems(custIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void setCellValueFactory() {
        col_code.setCellValueFactory(new PropertyValueFactory<>("code"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colunitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("button"));
    }
    private void loadTime() {

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd:MMM:yyyy");
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            lblate.setText(dateFormat1.format(new Date()));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    public void AddOnAction(ActionEvent actionEvent) {
        String menuItemCode = cmbitem_code.getValue();
        String status = txtstatus.getText();
        int qty = Integer.parseInt(txtqty.getText());
        int unitPrice = Integer.parseInt(txtu_price.getText());
        int total = qty * unitPrice;

        Button removeButton = new Button("Remove");
        removeButton.setCursor(Cursor.HAND);

        removeButton.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int index = tblOrders.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    CartTm remove = (CartTm) tblOrders.getItems().remove(index);
                    list.remove(remove);
                    System.out.println(list.size());
                    calculateNetTotal();
                } else {
                    System.out.println("No item selected.");
                }
            }
        });

        CartTm cartTm = new CartTm();
        cartTm.setCode(menuItemCode);
        cartTm.setUnitPrice(Double.parseDouble(txtu_price.getText()));
        cartTm.setQty(qty);
        cartTm.setAmount(total);
        cartTm.setButton(removeButton);

        list.add(cartTm);
        tblOrders.getItems().add(cartTm);
        calculateNetTotal();
        clearItemsFields();
    }


    private void clearItemsFields() {
        txtu_price.clear();
        txtqty.clear();
    }

    private void calculateNetTotal() {
        int total = 0;
        for (int i = 0; i < tblOrders.getItems().size(); i++) {
            total += (int) colamount.getCellData(i);
        }

        lblTotal.setText(String.valueOf(total));
    }

    public void PlaceOrderOnAction(ActionEvent actionEvent) {
        String oId = txtOrderID.getText();
        String customerId = cmbCustome_code.getValue();
        String date = String.valueOf(LocalDate.now());
        String type = cmbtype.getSelectionModel().getSelectedItem();
        String status = "Not Paid";
        int total = Integer.parseInt(lblTotal.getText());

        OrdersDto orderDto = new OrdersDto(oId, date, type, status, total, customerId);

        boolean isSuccess = false;
        try {
            isSuccess = ordersBO.placeOrder(orderDto, list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (isSuccess) {
            new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();
        }
    }

    public void orderdetailsOnAction(ActionEvent actionEvent) throws IOException {


        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/OrderDetails_form.fxml")));

    }

    public void cmbCustomeOnAction(ActionEvent actionEvent) {

    }

    public void cmbitemOnAction(ActionEvent actionEvent) {

        String code = cmbitem_code.getValue();

        try {
            MenuItemDto dto = ordersBO.searchMenuItem(code);
            txtu_price.setText(dto.getUnit_price());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void txtqtyOnAction(ActionEvent actionEvent) {

        int qty = Integer.parseInt(txtqty.getText());
        int unitPrice = Integer.parseInt(txtu_price.getText());
        int total = qty * unitPrice;

        txtamount.setText(String.valueOf(total));
    }
}

