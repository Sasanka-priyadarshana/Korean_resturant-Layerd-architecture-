package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockItemDto {

    private  String stock_item_id ;
    private  String stock_name ;
    private  String qty ;
    private  String unit_price;
}
