package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.bo.custom.impl.UserBOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class loginFormController {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private AnchorPane Pane;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            boolean isIn = userBO.loginUser(username,password);
            if (!isIn) {
                new Alert(Alert.AlertType.WARNING, "Login details incorrect").show();
            }else {
                navigate.navigate(Pane,"main_form.fxml");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    public void btnCreateanAccountOnAction(ActionEvent actionEvent) throws IOException {
        navigate.navigate(Pane,"signup_form.fxml");
    }
}
