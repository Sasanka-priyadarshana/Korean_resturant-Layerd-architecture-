package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class Orders {
    private String o_id;
    private String date;
    private String type;
    private String status;
    private int total;
    private String c_id;
}