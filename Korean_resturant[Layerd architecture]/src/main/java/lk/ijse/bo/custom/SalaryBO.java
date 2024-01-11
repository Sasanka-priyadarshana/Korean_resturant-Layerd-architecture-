package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.SalaryDto;

import java.sql.SQLException;
import java.util.List;

public interface SalaryBO extends SuperBO {
      boolean deleteSalary(String s_id) throws SQLException;

      List<SalaryDto> getAllSalary() throws SQLException;


     boolean addSalary(SalaryDto dto) throws SQLException ;

     boolean updateSalary(SalaryDto salaryDto) throws SQLException;

     SalaryDto searchSalary(String code) throws SQLException ;
}
