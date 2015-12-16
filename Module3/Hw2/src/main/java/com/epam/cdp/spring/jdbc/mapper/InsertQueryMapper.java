package com.epam.cdp.spring.jdbc.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Yurii_Chukhlatyi on 11.08.2015.
 */
public interface InsertQueryMapper<T> {
    void mapQuery(T entity, PreparedStatement statement) throws SQLException;
}
