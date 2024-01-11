package lk.ijse.bo.custom.impl;

import javafx.scene.chart.XYChart;
import lk.ijse.bo.custom.StockItemBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StockItemDAO;
import lk.ijse.dao.custom.impl.StockItemDAOImpl;
import lk.ijse.dto.PaymentDto;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.StockItemDto;
import lk.ijse.entity.Payment;
import lk.ijse.entity.StockItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockItemBOImpl implements StockItemBO {
    StockItemDAO stockItemDAO = (StockItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCKITEM);

    @Override
    public boolean deleteStockItem(String stock_item_id) throws SQLException {
        return stockItemDAO.delete(stock_item_id);
    }

    @Override
    public XYChart.Series getStockItemChart() throws SQLException {
        List<StockItem> stockItems = stockItemDAO.getAll();

        XYChart.Series series = new XYChart.Series();

        for (StockItem dto : stockItems) {
            int qty = dto.getQty();
            String desc = dto.getStock_name();

            series.getData().add(new XYChart.Data(desc, qty));

        }
        return series;


    }

    @Override
    public boolean addStockItem(StockItemDto dto) throws SQLException {
        return stockItemDAO.save(new StockItem(dto.getStock_item_id(),dto.getStock_name(),Integer.parseInt(dto.getQty()),dto.getUnit_price()));
    }

    @Override
    public boolean updateStockItem(StockItemDto stockItemDto) throws SQLException {
        return stockItemDAO.update(new StockItem(stockItemDto.getStock_item_id(),stockItemDto.getStock_name(),Integer.parseInt(stockItemDto.getQty()),stockItemDto.getUnit_price()));
    }

    @Override
    public StockItemDto searchStockItem(String code) throws SQLException {
        StockItem stockItem = stockItemDAO.search(code);
        return new StockItemDto(stockItem.getStock_item_id(),stockItem.getStock_name(),String.valueOf(stockItem.getQty()),stockItem.getUnit_price());
    }

    @Override
    public List<StockItemDto> getAllStockItem() throws SQLException {
        List<StockItem> list = stockItemDAO.getAll();
        List<StockItemDto> dtoList = new ArrayList<>();

        for (StockItem stockItem : list) {
            dtoList.add(new StockItemDto(stockItem.getStock_item_id(),stockItem.getStock_name(),String.valueOf(stockItem.getQty()),stockItem.getUnit_price()));
        }
        return dtoList;
    }
}
