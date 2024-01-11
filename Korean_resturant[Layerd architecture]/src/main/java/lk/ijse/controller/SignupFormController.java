package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDto;
import lk.ijse.bo.custom.impl.UserBOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class SignupFormController {
    public AnchorPane CreatePane;
    public PasswordField txtcpw;
    public PasswordField txtpw;
    public TextField txtUsername;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnSignupOnAction(ActionEvent event) throws IOException {



        String username=txtUsername.getText();
        String password=txtpw.getText();
        String confirmPassword=txtcpw.getText();

        if (password.equals(confirmPassword)){
        var dto = new UserDto(username , confirmPassword);
            try {
                boolean isSaved = userBO.saveUser(dto);
                if (isSaved){

                    new Alert(Alert.AlertType.CONFIRMATION,"User save confirm!").show();
                } else {

                new Alert(Alert.AlertType.CONFIRMATION,"User Not save").show();
                }
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else new Alert(Alert.AlertType.ERROR,"Password not matched!").show();

    }

    public void btnCreateanAccountOnAction(ActionEvent actionEvent) throws IOException {

        CreatePane.getChildren().clear();
        CreatePane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/login_form.fxml")));
    }

}

