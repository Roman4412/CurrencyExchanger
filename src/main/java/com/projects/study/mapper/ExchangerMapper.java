package com.projects.study.mapper;


import java.sql.ResultSet;
import java.util.Map;

public interface ExchangerMapper<T> {
    T toEntity(ResultSet resultSet);

    T toEntity(Map<String, String[]> params);

}
