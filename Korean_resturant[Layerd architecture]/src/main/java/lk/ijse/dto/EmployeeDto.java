package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {
    private  String e_id;
    private  String email;
    private  String address;
    private  int  tel_number;
    private String username;

}
