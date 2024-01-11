package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.MenuItemDAO;
import lk.ijse.dto.MenuItemDto;
import lk.ijse.entity.MenuItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAOImpl implements MenuItemDAO {

    @Override
    public List<MenuItem> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM menu_item");

        List<MenuItem> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new MenuItem(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            ));
        }
        return dtoList;
    }

    @Override
    public boolean save(MenuItem dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO menu_item VALUES (?,?,?,?)",dto.getItem_code(),dto.getUnit_price(),dto.getDescription(),dto.getName());

    }

    @Override
    public boolean update(MenuItem dto) throws SQLException {
        return SQLUtil.execute( "UPDATE menu_item SET unit_price =? ,description =? ,name =? WHERE item_code =?",dto.getItem_code(),
                dto.getUnit_price(),
                dto.getDescription(),
                dto.getName());

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM menu_item WHERE item_code = ?",id);

    }

    @Override
    public MenuItem search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM menu_item WHERE item_code = ?",id);

        MenuItem dto = null;

        if(resultSet.next()) {
            dto = new MenuItem(
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
