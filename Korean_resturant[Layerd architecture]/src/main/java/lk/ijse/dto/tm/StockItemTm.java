package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StockItemTm {

    private  String stock_item_id ;
    private  String stock_name ;
    private  String qty ;
    private  String unit_price;
}


