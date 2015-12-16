package com.epam.cdp.spring.jdbc;

import com.epam.cdp.spring.jdbc.mapper.InsertQueryMapper;
import com.epam.cdp.spring.jdbc.mapper.UpdateQueryMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExtendedJDBCTemplate extends JdbcTemplate implements ExtendedJDBCOperations {

    public ExtendedJDBCTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <T> void add(String sql, InsertQueryMapper<T> mapper, T entity) {
        super.update(createPreparedStatement(sql, entity, mapper));
    }

    @Override
    public <T> Number addAndReturnGeneratedKey(String sql, InsertQueryMapper<T> mapper, T entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatement = createPreparedStatement(sql, entity, mapper);
        if (super.update(preparedStatement, keyHolder) > 0) {
            return keyHolder.getKey();
        }
        return null;
    }

    @Override
    public <T> List<T> queryForObjectList(String sql, Class<T> requiredType, Object... args) {
        return super.query(sql, resultSet -> {
            List<T> result = new ArrayList<T>();
            while (resultSet.next()) {
                result.add((T) resultSet.getObject(1));
            }
            return result;
        }, args);
    }


    @Override
    public <T> void batchUpdate(String sql, InsertQueryMapper<T> mapper, List<T> entities) {
        super.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                mapper.mapQuery(entities.get(i), ps);
            }

            @Override
            public int getBatchSize() {
                return entities.size();
            }
        });
    }

    @Override
    public <T> int update(String sql, UpdateQueryMapper<T> mapper, T entity) {
        return super.update(sql, mapper, entity);
    }

    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
        try {
            return super.queryForObject(sql, rowMapper, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
        try {
            return super.queryForObject(sql, requiredType);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) throws DataAccessException {
        try {
            return super.queryForObject(sql, requiredType, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private <T> PreparedStatementCreator createPreparedStatement(String sql, T entity, InsertQueryMapper<T> mapper) {
        return connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mapper.mapQuery(entity, preparedStatement);
            return preparedStatement;
        };
    }
}

