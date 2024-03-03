package com.projects.study.DAO;

import java.util.List;
import java.util.Optional;

public interface DAO <T> {
    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void delete(long id);
}
