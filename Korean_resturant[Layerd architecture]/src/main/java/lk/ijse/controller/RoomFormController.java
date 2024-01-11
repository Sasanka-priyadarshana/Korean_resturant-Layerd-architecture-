package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.RoomBO;
import lk.ijse.dto.RoomDto;
import lk.ijse.dto.tm.RoomTm;
import lk.ijse.bo.custom.impl.RoomBOImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomFormController {
    public TextField txtRoomNO;
    public TextField txtAvailability;
    public TextField txtDescription;
    public TableColumn colr_no;
    public TableColumn colavailability;
    public TableColumn coldescription;
    public TableView tblRoom;

    RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }

    private void loadAllItems() {
        ObservableList<RoomTm> obList = FXCollections.observableArrayList();

        try {
            List<RoomDto> list = roomBO.getAllRoom();
            for (RoomDto dto: list){
                obList.add(new RoomTm(
                        dto.getRoom_no(),
                        dto.getDescription(),
                        dto.getAvailability()
                ));
            }
            tblRoom.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colr_no.setCellValueFactory(new PropertyValueFactory<>("room_no"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colavailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

    }

    public void AddOnAction(ActionEvent actionEvent) {
        boolean isRoomValid = validateRoom();
        if (isRoomValid) {
            String no = txtRoomNO.getText();
            String description = txtDescription.getText();
            String availability = txtAvailability.getText();

            RoomDto dto = new RoomDto(no, description, availability);

            try {
                boolean isSaved = roomBO.addRoom(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, " Room Saved!", ButtonType.OK).show();
                    clearFields();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Room Not Saved!", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Room Details", ButtonType.OK).show();
        }

    }

    private boolean validateRoom() {

        String RoomIdText =txtRoomNO.getText();
        Pattern compile = Pattern.compile("[R][0-9]{3,}");
        Matcher matcher = compile.matcher(RoomIdText);
        boolean matches = matcher.matches();

        if (!matches) {
            new Alert(Alert.AlertType.ERROR, "Invalid Room NO").show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtRoomNO.setText("");
        txtDescription.setText("");
        txtAvailability.setText("");
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
        String no = txtRoomNO.getText();

        try {
            boolean isDeleted = roomBO.deleteRoom(no);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Room deleted!").show();
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
        String code = txtRoomNO.getText();

        try {
            RoomDto dto = roomBO.searchRoom(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Rooms not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(RoomDto dto) {
        txtRoomNO.setText("");
        txtDescription.setText("");
        txtDescription.setText("");
    }

    public void UpdateOnAction(ActionEvent actionEvent) {

        String no = txtRoomNO.getText();
        String description = txtDescription.getText();
        String availability = txtAvailability.getText();
        try {
            boolean isUpdated = roomBO.updateRoom(new RoomDto(no, description,availability ));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Rooms updated!").show();
                clearFields();
                loadAllItems();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
