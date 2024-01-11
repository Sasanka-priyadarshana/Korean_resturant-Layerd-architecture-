package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.RoomDAO;
import lk.ijse.dto.RoomDto;
import lk.ijse.entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public List<Room> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM room");


        List<Room> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new Room(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return dtoList;
    }

    @Override
    public boolean save(Room dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO room VALUES (?,?,?)",dto.getRoom_no(),dto.getDescription(),dto.getAvailability());

    }

    @Override
    public boolean update(Room dto) throws SQLException {
        return SQLUtil.execute("UPDATE room SET description =? ,availability =? WHERE room_no =?",dto.getRoom_no(), dto.getDescription(), dto.getAvailability());

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM  room  WHERE room_no = ?",id);

    }

    @Override
    public Room search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM room WHERE room_no = ?",id);

        Room dto = null;

        if(resultSet.next()) {
            dto = new Room(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return dto;
    }

    @Override
    public String getLastId() throws SQLException {
        return null;
    }
}
