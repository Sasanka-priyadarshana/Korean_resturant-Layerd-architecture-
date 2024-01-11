package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.RoomBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.RoomDAO;
import lk.ijse.dao.custom.impl.RoomDAOImpl;
import lk.ijse.dto.MenuItemDto;
import lk.ijse.dto.RoomDto;
import lk.ijse.entity.MenuItem;
import lk.ijse.entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomBOImpl implements RoomBO {
    RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public List<RoomDto> getAllRoom() throws SQLException {
        List<Room> list = roomDAO.getAll();
        List<RoomDto> dtoList = new ArrayList<>();

        for (Room room : list) {
            dtoList.add(new RoomDto(room.getRoom_no(),room.getDescription(),room.getAvailability()));
        }
        return dtoList;
    }

    @Override
    public boolean deleteRoom(String room_no) throws SQLException {
        return roomDAO.delete(room_no);
    }

    @Override
    public boolean addRoom(RoomDto dto) throws SQLException {
        return roomDAO.save(new Room(dto.getRoom_no(),dto.getDescription(),dto.getAvailability()));
    }

    @Override
    public RoomDto searchRoom(String code) throws SQLException {
        Room room = roomDAO.search(code);
       return new RoomDto(room.getRoom_no(),room.getDescription(),room.getAvailability());
    }

    @Override
    public boolean updateRoom(RoomDto roomDto) throws SQLException {
        return roomDAO.update(new Room(roomDto.getRoom_no(),roomDto.getDescription(),roomDto.getAvailability()));
    }
}
