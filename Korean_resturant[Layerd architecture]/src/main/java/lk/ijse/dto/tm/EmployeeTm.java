package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeTm {
    private  String e_id;
    private  String email;
    private  String address;
    private  int  tel_number;
    private String username;

}

