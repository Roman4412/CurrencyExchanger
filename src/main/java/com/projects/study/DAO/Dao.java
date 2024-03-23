package com.projects.study.DAO;

import java.util.Optional;
import java.util.stream.Stream;

public interface Dao<T> {
    Optional<T> getByCode(String code);

    Stream<T> getAll();

    T save(T t);

    boolean update(T t);

}
