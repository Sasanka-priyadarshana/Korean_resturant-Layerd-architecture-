package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.AttendanceBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.AttendanceDAO;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dto.AttendanceDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.entity.Attendance;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBOImpl implements AttendanceBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);

    @Override
    public boolean deleteAttendance(String a_id) throws SQLException {
        return attendanceDAO.delete(a_id);

    }

    @Override
    public List<AttendanceDto> getAllAttendance() throws SQLException {

        List<Attendance> list = attendanceDAO.getAll();
        List<AttendanceDto> dtoList = new ArrayList<>();

        for (Attendance attendance : list) {
            dtoList.add(new AttendanceDto(attendance.getA_id(), attendance.getTime(),attendance.getDate(),attendance.getE_id()));
        }
        return dtoList;

    }

    @Override
    public AttendanceDto searchAttendance(String code) throws SQLException {
        Attendance attendance =  attendanceDAO.search(code);
        return new AttendanceDto(attendance.getA_id() ,attendance.getTime(),attendance.getDate(),attendance.getE_id());
    }

    @Override
    public boolean addAttendance(AttendanceDto dto) throws SQLException {
        return attendanceDAO.save( new Attendance(dto.getA_id(),dto.getEId(),dto.getDate(),dto.getTime()));
    }

    @Override
    public boolean updateAttendance(AttendanceDto dto) throws SQLException {
        return attendanceDAO.update(new Attendance(dto.getA_id(),dto.getTime(),dto.getDate(),dto.getEId()));
    }

    @Override
    public List<EmployeeDto> getAllEmployee() throws SQLException {

        List<Employee> list = employeeDAO.getAll();
        List<EmployeeDto> dtoList = new ArrayList<>();

        for (Employee employee : list) {
            dtoList.add(new EmployeeDto(employee.getE_id(),employee.getEmail(),employee.getAddress(),employee.getTel_number(),employee.getUsername()));
        }
        return dtoList;
    }
}
