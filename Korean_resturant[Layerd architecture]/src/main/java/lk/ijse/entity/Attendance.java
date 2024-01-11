package lk.ijse.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Attendance {

    private String a_id;
    private String e_id;
    private String date;
    private String time;

}