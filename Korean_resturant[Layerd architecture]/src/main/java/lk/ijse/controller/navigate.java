package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class navigate {

    public static void navigate(AnchorPane Pane, String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(navigate.class.getResource("/view/"+path));
        AnchorPane newScene = fxmlLoader.load();
        Pane.getChildren().clear();
        Pane.getChildren().setAll(newScene);
    }
    public static void switchNavigation(String link, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(navigate.class.getResource("/view/" + link));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }



    public static void changePane(Pane pane, String path) throws IOException {
        Parent parent = FXMLLoader.load(navigate.class.getResource("/view/"+path));
        pane.getChildren().clear();
        pane.getChildren().add(parent);

    }
}
