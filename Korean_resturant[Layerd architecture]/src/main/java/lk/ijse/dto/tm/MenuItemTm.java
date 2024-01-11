package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MenuItemTm {

    private  String item_code;
    private  String unit_price ;
    private  String description;
   private String name;
}
