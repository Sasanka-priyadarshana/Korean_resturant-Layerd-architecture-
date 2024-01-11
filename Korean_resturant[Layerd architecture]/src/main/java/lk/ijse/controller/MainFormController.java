package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import java.io.IOException;
public class MainFormController {

    @FXML

    private Pane pane;


    @FXML
    private Pane Pane;
    @FXML
    void DashBoardOmAction(ActionEvent event) throws IOException {
        navigate.changePane(Pane,"dashboard_form.fxml");

    }

    @FXML
    void CustomerOnAction(ActionEvent event) throws IOException {
        navigate.changePane(Pane,"customer_form.fxml");

    }

    @FXML
    void MenuItemOnAction(ActionEvent event) throws IOException {
        navigate.changePane(Pane,"menuItem_form.fxml");

    }

    @FXML
    void PaymentOnAction(ActionEvent event) throws IOException {
        navigate.changePane(Pane,"payment_form.fxml");

    }
    @FXML
    void RoomOnAction(ActionEvent event) throws IOException {
        navigate.changePane(Pane,"room_form.fxml");

    }
    @FXML
    void EmployeeOnAction(ActionEvent event) throws IOException {
        navigate.changePane(Pane,"employee_form.fxml");

    }
    @FXML
    void SupplierOnAction(ActionEvent event) throws IOException {
        navigate.changePane(Pane,"supplier_form.fxml");
    }
    @FXML
    void SignOutOmAction(ActionEvent event) throws IOException {

        navigate.switchNavigation("login_form.fxml",event);
    }

    @FXML
    void ReportsOnAction(ActionEvent event) throws IOException {
        navigate.changePane (Pane ,"reports_form.fxml");
    }
}
