package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SalaryBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SalaryDAO;
import lk.ijse.dao.custom.impl.SalaryDAOImpl;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.SalaryDto;
import lk.ijse.entity.Employee;
import lk.ijse.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryBOImpl implements SalaryBO {
    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);

    @Override
    public boolean deleteSalary(String s_id) throws SQLException {
        return salaryDAO.delete(s_id);
    }

    @Override
    public List<SalaryDto> getAllSalary() throws SQLException {
        List<Salary> list = salaryDAO.getAll();
        List<SalaryDto> dtoList = new ArrayList<>();

        for (Salary salary : list) {
            dtoList.add(new SalaryDto(salary.getS_id(),salary.getPayment(),salary.getDate(),salary.getE_id()));
        }
        return dtoList;
    }

    @Override
    public boolean addSalary(SalaryDto dto) throws SQLException {
        return salaryDAO.save(new Salary(dto.getS_id(),dto.getPayment(),dto.getDate(),dto.getE_id()));
    }

    @Override
    public boolean updateSalary(SalaryDto salaryDto) throws SQLException {
        return salaryDAO.update(new Salary(salaryDto.getS_id(),salaryDto.getPayment(),salaryDto.getDate(),salaryDto.getE_id()));
    }

    @Override
    public SalaryDto searchSalary(String code) throws SQLException {
        Salary salary = salaryDAO.search(code);
       return new SalaryDto(salary.getS_id(),salary.getPayment(),salary.getDate(),salary.getE_id());
    }

}
