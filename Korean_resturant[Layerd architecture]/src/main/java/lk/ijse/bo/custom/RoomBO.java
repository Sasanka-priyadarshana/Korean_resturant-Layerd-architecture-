package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.RoomDto;

import java.sql.SQLException;
import java.util.List;

public interface RoomBO extends SuperBO {
    List<RoomDto> getAllRoom() throws SQLException;

     boolean deleteRoom(String room_no) throws SQLException;

    boolean addRoom(RoomDto dto) throws SQLException;

    RoomDto searchRoom(String code) throws SQLException;

    boolean updateRoom(RoomDto roomDto) throws SQLException;
}
