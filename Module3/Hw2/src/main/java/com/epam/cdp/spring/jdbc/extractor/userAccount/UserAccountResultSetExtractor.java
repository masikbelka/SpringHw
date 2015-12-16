package com.epam.cdp.spring.jdbc.extractor.userAccount;

import com.epam.cdp.spring.model.UserAccount;
import com.epam.cdp.spring.model.impl.UserAccountImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yurii Chukhlatyi
 */
@Component
public class UserAccountResultSetExtractor implements ResultSetExtractor<List<UserAccount>> {
    @Override
    public List<UserAccount> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<UserAccount> userAccounts = new ArrayList<>();
        while (resultSet.next()) {
            UserAccount userAccount = getUserAccount(resultSet);
            userAccounts.add(userAccount);
        }
        return userAccounts;
    }

    private UserAccount getUserAccount(ResultSet resultSet) throws SQLException {
        UserAccount userAccount = new UserAccountImpl();
        userAccount.setUserAccountId(resultSet.getLong("user_account_id"));
        userAccount.setUserId(resultSet.getLong("user_id"));
        userAccount.setPrepaidMoney(resultSet.getDouble("prepaid_money"));
        return userAccount;
    }
}
