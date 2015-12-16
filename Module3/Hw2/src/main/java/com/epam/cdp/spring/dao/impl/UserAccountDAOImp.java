package com.epam.cdp.spring.dao.impl;

import com.epam.cdp.spring.dao.UserAccountDAO;
import com.epam.cdp.spring.jdbc.ExtendedJDBCTemplate;
import com.epam.cdp.spring.jdbc.mapper.impl.userAccount.UserAccountMapper;
import com.epam.cdp.spring.model.UserAccount;
import com.epam.cdp.spring.model.impl.UserAccountImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Yurii Chukhlatyi
 */
@Repository
public class UserAccountDAOImp implements UserAccountDAO {

    private static final String CREATE = "INSERT INTO user_account (user_id, funds) VALUES (?, ?)";
    private static final String GET_BY_USER = "SELECT * FROM user_account WHERE user_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM user_account WHERE user_account_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM user_account WHERE user_account_id = ?";
    private static final String UPDATE = "UPDATE user_account SET funds = ? WHERE user_account_id = ?";
    private static final String DELETE_BY_USER_ID = "DELETE FROM user_account WHERE user_id = ?";
    public static final String IS_USER_ACCOUNT_FOR_USER_EXIST = "SELECT COUNT(*) FROM user_account WHERE user_id = ?";
    public static final String IS_USER_ACCOUNT_EXIST = "SELECT COUNT(*) FROM user_account WHERE user_account_id = ?";

    @Autowired
    private ExtendedJDBCTemplate extendedJDBCTemplate;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public UserAccount create(UserAccount userAccount) {
        UserAccount createdUserAccount = null;
        Number generatedId = extendedJDBCTemplate.addAndReturnGeneratedKey(CREATE, userAccountMapper, userAccount);
        if (generatedId != null) {
            createdUserAccount = cloneUserAccount(userAccount);
            createdUserAccount.setUserAccountId(generatedId.longValue());
        }
        return userAccount;
    }

    @Override
    public UserAccount getUserAccountByUserId(long userId) {
        return extendedJDBCTemplate.queryForObject(GET_BY_USER, userAccountMapper, userId);
    }

    @Override
    public UserAccount getUserAccountById(long userAccountId) {
        return extendedJDBCTemplate.queryForObject(GET_BY_ID, userAccountMapper, userAccountId);
    }

    @Override
    public boolean deleteUserAccountById(long userAccountId) {
        return extendedJDBCTemplate.update(DELETE_BY_ID, Integer.class, userAccountId) > 0;
    }

    @Override
    public boolean deleteUserAccountByUser(long userId) {
        return extendedJDBCTemplate.update(DELETE_BY_USER_ID, Integer.class, userId) > 0;
    }

    @Override
    public boolean update(UserAccount userAccount) {
        return extendedJDBCTemplate.update(UPDATE, userAccountMapper, userAccount) > 0;
    }

    @Override
    public boolean isUserAccountForUserExist(long userId) {
        return extendedJDBCTemplate.queryForObject(IS_USER_ACCOUNT_FOR_USER_EXIST, Integer.class, userId) > 0;
    }

    @Override
    public boolean isUserAccountExist(long userAccountId) {
        return extendedJDBCTemplate.queryForObject(IS_USER_ACCOUNT_EXIST, Integer.class, userAccountId) > 0;
    }


    private UserAccount cloneUserAccount(UserAccount userAccount) {
        UserAccount clonedUserAccount = new UserAccountImpl();
        userAccount.setUserId(userAccount.getUserId());
        userAccount.setPrepaidMoney(userAccount.getPrepaidMoney());
        return clonedUserAccount;
    }
}
