package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.SupplierDto;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    SupplierDto searchSupplier(String code) throws SQLException;

    boolean addSupplier(SupplierDto dto) throws SQLException;

    List<SupplierDto> getAllCustomer() throws SQLException;

    boolean deleteCustomer(String sup_id) throws SQLException;

    boolean updateSupplier(SupplierDto supplierDto) throws SQLException;
}