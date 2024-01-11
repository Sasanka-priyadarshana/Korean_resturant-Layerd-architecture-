package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.UserDto;
import lk.ijse.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(User dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO user VALUES (?,?)",dto.getUsername(),dto.getPw());
    }

    @Override
    public boolean update(User dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
return false;
    }

    @Override
    public User search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getLastId() throws SQLException {
        return null;
    }

    @Override
    public boolean loginUser(String username, String password) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE username=? AND pw=?",username,password);
        if (resultSet.next())return true;
        return false;
    }
}
