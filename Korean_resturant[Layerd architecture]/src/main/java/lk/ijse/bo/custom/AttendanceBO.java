package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.AttendanceDto;
import lk.ijse.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceBO extends SuperBO {
    boolean deleteAttendance(String a_id) throws SQLException ;

     List<AttendanceDto> getAllAttendance() throws SQLException;

     AttendanceDto searchAttendance(String code) throws SQLException;

    boolean addAttendance(AttendanceDto dto) throws SQLException;

    boolean updateAttendance(AttendanceDto dto) throws SQLException ;

    List<EmployeeDto> getAllEmployee() throws SQLException;

}

