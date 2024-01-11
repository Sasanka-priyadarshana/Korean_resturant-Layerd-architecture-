package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.AttendanceDAO;
import lk.ijse.dto.AttendanceDto;
import lk.ijse.entity.Attendance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    @Override
    public List<Attendance> getAll() throws SQLException {
        ResultSet resultSet =SQLUtil.execute("SELECT * FROM attendance");


        List<Attendance> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new Attendance(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return dtoList;
    }

    @Override
    public boolean save(Attendance dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO attendance VALUES (?,?,?,?)",dto.getA_id(),dto.getE_id(),dto.getDate(),dto.getTime());

    }

    @Override
    public boolean update(Attendance dto) throws SQLException {
        return SQLUtil.execute("UPDATE attendance SET date =? ,time =? WHERE a_id =?",dto.getA_id(),dto.getDate(),dto.getTime());

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM attendance WHERE a_id = ?",id);
    }

    @Override
    public Attendance search(String id) throws SQLException {
        ResultSet resultSet =SQLUtil.execute("SELECT * FROM attendance WHERE a_id = ?",id);

        Attendance dto = null;

        if(resultSet.next()) {
            dto = new Attendance(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return dto;
    }

    @Override
    public String getLastId() throws SQLException {
        return null;
    }
}
