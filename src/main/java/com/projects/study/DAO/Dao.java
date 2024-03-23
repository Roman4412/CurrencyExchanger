package com.projects.study.DAO;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> getByCode(String code);

    List<T> getAll();

    Optional<T> save(T t);

    boolean delete(long id);

    boolean update(T t);

}
