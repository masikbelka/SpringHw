package com.epam.cdp.spring.jdbc.mapper.impl.userAccount;

import com.epam.cdp.spring.jdbc.mapper.InsertQueryMapper;
import com.epam.cdp.spring.jdbc.mapper.UpdateQueryMapper;
import com.epam.cdp.spring.model.UserAccount;
import com.epam.cdp.spring.model.impl.UserAccountImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yurii Chukhlatyi
 */
@Component
public class UserAccountMapper implements InsertQueryMapper<UserAccount>, RowMapper<UserAccount>, UpdateQueryMapper<UserAccount> {
    @Override
    public void mapQuery(UserAccount entity, PreparedStatement statement) throws SQLException {
        statement.setLong(1, entity.getUserId());
        statement.setDouble(2, entity.getPrepaidMoney());
    }

    @Override
    public UserAccount mapRow(ResultSet resultSet, int i) throws SQLException {
        UserAccount userAccount = new UserAccountImpl();
        userAccount.setUserAccountId(resultSet.getLong("user_account_id"));
        userAccount.setUserId(resultSet.getLong("user_id"));
        userAccount.setPrepaidMoney(resultSet.getDouble("prepaid_money"));
        return null;
    }

    @Override
    public Object[] mapUpdateQuery(UserAccount entity) {
        Object[] params = new Object[2];
        params[0] = entity.getPrepaidMoney();
        params[1] = entity.getUserAccountId();
        return params;
    }
}
