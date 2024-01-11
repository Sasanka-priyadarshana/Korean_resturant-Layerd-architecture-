package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class StockItem {
    private String stock_item_id;
    private String stock_name;
    private int qty;
    private String unit_price;
}
