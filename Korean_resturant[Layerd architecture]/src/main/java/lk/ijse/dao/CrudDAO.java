package lk.ijse.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    List<T> getAll() throws SQLException;

    boolean save(T dto) throws SQLException;

    boolean update(T dto) throws SQLException;

    boolean delete(String id) throws SQLException;

    T search(String id) throws SQLException;

    String getLastId() throws SQLException;
}
