package lk.ijse.dto.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceTm {

    private String a_id;
    private String time;
    private String date;
    private String eId;
}
