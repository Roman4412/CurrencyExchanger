package com.projects.study.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> getByCode(String code);

    List<T> getAll() throws SQLException;

    void save(T t);

    void delete(long id);

}
