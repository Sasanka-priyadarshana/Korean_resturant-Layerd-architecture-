package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.dto.UserDto;
import lk.ijse.entity.User;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveUser(UserDto userDto) throws SQLException {
        return userDAO.save(new User(userDto.getUserName(),Integer.parseInt(userDto.getPw())));
    }

    @Override
    public boolean loginUser(String username, String password) throws SQLException {
        return userDAO.loginUser(username, password);
    }
}