package nsu.fit.db.dao;

import nsu.fit.db.model.MNode;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T> {
    T getElement(long elementId) throws SQLException;

    void insert(T element) throws SQLException;

    void insertPrepared(T element) throws SQLException;

    void insertBatch(List<T> element) throws SQLException;
}
