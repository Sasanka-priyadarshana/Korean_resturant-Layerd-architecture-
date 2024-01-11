package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.UserDto;
import lk.ijse.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    boolean loginUser(String username, String password) throws SQLException;

}
