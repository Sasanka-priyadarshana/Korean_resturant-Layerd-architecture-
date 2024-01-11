package lk.ijse.dto;

import lk.ijse.dto.tm.CartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PlaceOrderDto {

    String item_code;
    Date date;
    int amount;
    List<CartTm> cartTmList;

}
