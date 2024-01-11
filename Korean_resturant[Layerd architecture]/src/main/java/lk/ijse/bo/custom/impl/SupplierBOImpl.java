package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.entity.Salary;
import lk.ijse.entity.Suppliers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public SupplierDto searchSupplier(String code) throws SQLException {
        Suppliers suppliers = supplierDAO.search(code);
        return new SupplierDto(suppliers.getSup_id(),suppliers.getAddress(),suppliers.getUsername());

    }

    @Override
    public boolean addSupplier(SupplierDto dto) throws SQLException {
        return supplierDAO.save(new Suppliers(dto.getSup_id(),dto.getAddress(),dto.getU_id()));
    }

    @Override
    public List<SupplierDto> getAllCustomer() throws SQLException {
        List<Suppliers> list = supplierDAO.getAll();
        List<SupplierDto> dtoList = new ArrayList<>();

        for (Suppliers suppliers : list) {
            dtoList.add(new SupplierDto(suppliers.getSup_id(),suppliers.getAddress(),suppliers.getUsername()));
        }
        return dtoList;
    }

    @Override
    public boolean deleteCustomer(String sup_id) throws SQLException {
        return supplierDAO.delete(sup_id);
    }

    @Override
    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        return supplierDAO.update(new Suppliers(supplierDto.getSup_id(),supplierDto.getAddress(),supplierDto.getU_id()));
    }
}
