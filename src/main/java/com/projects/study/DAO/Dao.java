package com.projects.study.DAO;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> getByCode(String code);

    List<T> getAll();

    void save(T t);

    void delete(long id);

}
