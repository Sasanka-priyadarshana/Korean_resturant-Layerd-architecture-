package lk.ijse.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrdersDto {

    private  String o_id;
    private String date;
    private String type;
    private  String status;
    private double total;
    private String c_id;
}
