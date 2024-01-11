package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.OrdersBO;
import lk.ijse.dto.OrdersDto;
import lk.ijse.dto.tm.OrdersTm;
import lk.ijse.bo.custom.impl.OrdersBOImpl;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailsFormController {
    public TableColumn colorder;
    public TableColumn colType;
    public TableColumn colcId;
    public TableColumn coldate;
    public TableColumn colstatus;
    public TableView tblOrders;

    OrdersBO ordersBO = (OrdersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERS);

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }

    private void loadAllItems() {
        ObservableList<Object> obList = FXCollections.observableArrayList();

        try {
            List<OrdersDto> list = ordersBO.getAllOrders();
            for (OrdersDto dto: list){
                obList.add(new OrdersTm(
                        dto.getO_id(),
                        dto.getDate(),
                        dto.getType(),
                        dto.getStatus(),
                        dto.getC_id()
                ));
            }
            tblOrders.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        colorder.setCellValueFactory(new PropertyValueFactory<>("o_id"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colcId.setCellValueFactory(new PropertyValueFactory<>("c_id"));


    }


}
