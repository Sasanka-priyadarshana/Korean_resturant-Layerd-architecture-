package lk.ijse.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuItemDto {


    private  String item_code;
    private  String unit_price ;
    private  String description;
    private String name;

}

