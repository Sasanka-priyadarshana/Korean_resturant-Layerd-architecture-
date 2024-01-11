package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.MenuItemBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.MenuItemDAO;
import lk.ijse.dao.custom.impl.MenuItemDAOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.MenuItemDto;
import lk.ijse.entity.Customer;
import lk.ijse.entity.MenuItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItemBOImpl implements MenuItemBO {
    MenuItemDAO menuItemDAO =(MenuItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MENUITEM);

    @Override
    public boolean deleteMenuItem(String item_code) throws SQLException {
        return menuItemDAO.delete(item_code);
    }

    @Override
    public boolean addMenuItem(MenuItemDto dto) throws SQLException {
        return menuItemDAO.save(new MenuItem(dto.getItem_code(),dto.getUnit_price(),dto.getDescription(),dto.getName()));
    }

    @Override
    public boolean updateMenuItem(MenuItemDto menuItemDto) throws SQLException {
        return menuItemDAO.update(new MenuItem(menuItemDto.getItem_code(),menuItemDto.getUnit_price(),menuItemDto.getDescription(),menuItemDto.getName()));

    }

    @Override
    public MenuItemDto searchMenuItem(String code) throws SQLException {
        MenuItem menuItem = menuItemDAO.search(code);
       return new MenuItemDto(menuItem.getItem_code(), menuItem.getUnit_price(), menuItem.getDescription(), menuItem.getName());

    }

    @Override
    public List<MenuItemDto> getAllMenuItem() throws SQLException {
        List<MenuItem> list = menuItemDAO.getAll();
        List<MenuItemDto> dtoList = new ArrayList<>();

        for (MenuItem menuItem : list) {
            dtoList.add(new MenuItemDto(menuItem.getItem_code(),menuItem.getUnit_price(),menuItem.getDescription(),menuItem.getName()));
        }
        return dtoList;

    }
}

