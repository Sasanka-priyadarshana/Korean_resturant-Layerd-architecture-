package lk.ijse.bo.custom;

import javafx.scene.chart.XYChart;
import lk.ijse.bo.SuperBO;
import lk.ijse.dto.StockItemDto;

import java.sql.SQLException;
import java.util.List;

public interface StockItemBO extends SuperBO {
     XYChart.Series getStockItemChart() throws SQLException;

     boolean deleteStockItem(String stock_item_id) throws SQLException;

     boolean addStockItem(StockItemDto dto) throws SQLException;

     boolean updateStockItem(StockItemDto stockItemDto) throws SQLException;

     StockItemDto searchStockItem(String code) throws SQLException;
     List<StockItemDto> getAllStockItem() throws SQLException;

}
