package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.StockItemDAO;
import lk.ijse.dto.StockItemDto;
import lk.ijse.entity.StockItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockItemDAOImpl implements StockItemDAO {
    @Override
    public List<StockItem> getAll() throws SQLException {
        ResultSet resultSet =SQLUtil.execute("SELECT * FROM stock_item");


        List<StockItem> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new StockItem(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4)
            ));
        }
        return dtoList;
    }

    @Override
    public boolean save(StockItem dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO stock_item VALUES (?,?,?,?)",dto.getStock_item_id(),dto.getStock_name(),dto.getQty(),dto.getUnit_price());

    }

    @Override
    public boolean update(StockItem dto) throws SQLException {
        return SQLUtil.execute("UPDATE stock_item SET stock_name =? ,qty =? ,unit_price =? WHERE stock_item_id =?",
                dto.getStock_item_id(),
                dto.getStock_name(),
                dto.getQty(),
                dto.getUnit_price());

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM stock_item WHERE stock_item_id = ?",id);

    }

    @Override
    public StockItem search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM stock_item WHERE stock_item_id = ?",id);

        StockItem dto = null;

        if(resultSet.next()) {
            dto = new StockItem(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
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
