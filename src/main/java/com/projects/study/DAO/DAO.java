package com.projects.study.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO <T> {
    Optional<T> getById(long id) throws SQLException;

    List<T> getAll();

    void save(T t);

    void delete(long id);
}
