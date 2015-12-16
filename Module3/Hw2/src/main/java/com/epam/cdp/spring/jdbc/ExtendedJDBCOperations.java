package com.epam.cdp.spring.jdbc;

import com.epam.cdp.spring.jdbc.mapper.InsertQueryMapper;
import com.epam.cdp.spring.jdbc.mapper.UpdateQueryMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Created by Yurii Chukhlatyi
 */
public interface ExtendedJDBCOperations extends JdbcOperations {
    <T> void add(String sql, InsertQueryMapper<T> mapper, T entity);

    <T> Number addAndReturnGeneratedKey(String sql, InsertQueryMapper<T> mapper, T entity);

    @Override
    <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException;

    @Override
    <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException;

    @Override
    <T> T queryForObject(String sql, Class<T> requiredType, Object... args) throws DataAccessException;

    <T> List<T> queryForObjectList(String sql, Class<T> requiredType, Object... args);

    <T> void batchUpdate(String sql, InsertQueryMapper<T> mapper, List<T> entities);

    <T> int update(String sql, UpdateQueryMapper<T> mapper, T entity);
}

