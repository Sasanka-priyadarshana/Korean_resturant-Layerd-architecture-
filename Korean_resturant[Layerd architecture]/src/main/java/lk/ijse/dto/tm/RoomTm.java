package lk.ijse.dto.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RoomTm {

    private String room_no;
    private String description;
    private String availability;
}
