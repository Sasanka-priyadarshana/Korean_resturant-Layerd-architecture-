package lk.ijse.controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.AttendanceBO;
import lk.ijse.dto.AttendanceDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.AttendanceTm;
import lk.ijse.bo.custom.impl.AttendanceBOImpl;
import lk.ijse.bo.custom.impl.EmployeeBOImpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttendanceFormController {

    public TextField txtAid;
    public Label lblTime;
    public TableView tblAttendence;
    public TableColumn colA_Id;
    public TableColumn colTime;
    public TableColumn colDate;
    public Label lbldate;
    public ComboBox cmbEId;
    public TableColumn colEId;


    AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ATTENDANCE);

    public void initialize() {
        setCellValueFactory();
        loadAllItems();
        loadTime();
        loadEmployee();
    }

    private void loadEmployee() {
        try {
            List<EmployeeDto> allEmployee = attendanceBO.getAllEmployee();
            ObservableList<String> id = FXCollections.observableArrayList();

            for (EmployeeDto dto : allEmployee) {
                id.add(dto.getE_id());
            }

            cmbEId.setItems(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd:MMM:yyyy");
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            lblTime.setText(dateFormat.format(new Date()));
            lbldate.setText(dateFormat1.format(new Date()));

        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    private void loadAllItems() {

        ObservableList<AttendanceTm> obList = FXCollections.observableArrayList();

        try {
            List<AttendanceDto> list = attendanceBO.getAllAttendance();
            for (AttendanceDto dto : list) {
                obList.add(new AttendanceTm(
                        dto.getA_id(),
                        dto.getTime(),
                        dto.getDate(),
                        dto.getEId()
                ));
            }
            tblAttendence.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {

        colA_Id.setCellValueFactory(new PropertyValueFactory<>("a_id"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEId.setCellValueFactory(new PropertyValueFactory<>("eId"));

    }

    public void SearchOnAction(ActionEvent actionEvent) {

        String code = txtAid.getText();

        try {
            AttendanceDto dto = attendanceBO.searchAttendance(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Attendance not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(AttendanceDto dto) {

        txtAid.setText(dto.getA_id());
        lbldate.setText(dto.getDate());
        lblTime.setText(dto.getTime());
    }

    public void AddOnAction(ActionEvent actionEvent) {

        boolean isAttendanceValid = validateAttendance();
        if (isAttendanceValid) {
            String id = txtAid.getText();
            String eId = String.valueOf(cmbEId.getSelectionModel().getSelectedItem());
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            String date = LocalDate.now().format(dateFormatter);
            String time = LocalTime.now().format(timeFormatter);

            AttendanceDto dto = new AttendanceDto(id, time, date, eId);

            try {
                AttendanceBOImpl attendanceModel = new AttendanceBOImpl();
                boolean isSaved = attendanceModel.addAttendance(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, " Attendance Saved!", ButtonType.OK).show();
                    clearFields();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Attendance Not Saved!", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Attendance Details", ButtonType.OK).show();
        }
    }

    private void clearFields() {

        txtAid.setText("");
        lbldate.setText("");
        lblTime.setText("");
    }

    private boolean validateAttendance() {

        String AttedanceIdText = txtAid.getText();
        Pattern compile = Pattern.compile("[A][0-9]{3,}");
        Matcher matcher = compile.matcher(AttedanceIdText);
        boolean matches = matcher.matches();

        if (!matches) {
            new Alert(Alert.AlertType.ERROR, "Invalid Attendance ID").show();
            return false;
        }
        return true;
    }

    public void DeleteOnAction(ActionEvent actionEvent) {


        String id = txtAid.getText();

        try {
            boolean isDeleted = attendanceBO.deleteAttendance(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Attendance deleted!").show();
                clearFields();
                loadAllItems();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void UpdateOnAction(ActionEvent actionEvent) {

        String id = txtAid.getText();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String date = LocalDate.now().format(dateFormatter);
        String time = LocalTime.now().format(timeFormatter);
        String eId = (String) cmbEId.getSelectionModel().getSelectedItem();

        try {
            boolean isUpdated = attendanceBO.updateAttendance(new AttendanceDto(id, time, date, eId));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Attendance updated!").show();
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
}
