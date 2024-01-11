package lk.ijse.controller;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import lk.ijse.bo.custom.StockItemBO;
import lk.ijse.bo.custom.impl.StockItemBOImpl;

import java.sql.SQLException;

public class DashboardFormController {

    public BarChart chartStock;
     private StockItemBO stockItemBO = new StockItemBOImpl();

    public void initialize() {
        barChartView();
    }

        private void barChartView() {
            try {

                XYChart.Series series = stockItemBO.getStockItemChart();

                chartStock.getData().addAll(series);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

         }

        }

