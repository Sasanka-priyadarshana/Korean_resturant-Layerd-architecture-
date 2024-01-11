package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrdersTm {
    private  String o_id;
    private String date;
    private String type;
    private String c_id;
    private String status;
    //private String total;
}
