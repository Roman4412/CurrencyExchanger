package com.projects.study.dao;

import java.util.Optional;
import java.util.stream.Stream;

public interface Dao<T> {
    Optional<T> get(String code);

    Stream<T> getAll();

    T save(T t);

    boolean update(T t);

    boolean isExist(String code);

}
