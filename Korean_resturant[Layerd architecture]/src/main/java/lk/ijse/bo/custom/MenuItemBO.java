package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.MenuItemDto;

import java.sql.SQLException;
import java.util.List;

public interface MenuItemBO extends SuperBO {
    boolean deleteMenuItem(String item_code) throws SQLException;

    boolean addMenuItem(MenuItemDto dto) throws SQLException;

    boolean updateMenuItem(MenuItemDto menuItemDto) throws SQLException;

    MenuItemDto searchMenuItem(String code) throws SQLException;

    List<MenuItemDto> getAllMenuItem() throws SQLException;
}
