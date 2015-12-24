package com.epam.cdp.spring.jdbc.mapper;

/**
 * Created by Yurii_Chukhlatyi on 11.08.2015.
 */
public interface UpdateQueryMapper<T> {
    Object[] mapUpdateQuery(T entity);
}
