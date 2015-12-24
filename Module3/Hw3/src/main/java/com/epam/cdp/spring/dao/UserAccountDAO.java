package com.epam.cdp.spring.dao;

import com.epam.cdp.spring.model.UserAccount;

/**
 * Created by Yurii Chukhlatyi
 */
public interface UserAccountDAO {
    UserAccount create(UserAccount userAccount);

    UserAccount getUserAccountByUserId(long userId);

    UserAccount getUserAccountById(long userAccountId);

    boolean deleteUserAccountById(long userAccountId);

    boolean deleteUserAccountByUser(long userId);

    boolean update(UserAccount userAccount);

    boolean isUserAccountForUserExist(long userId);

    boolean isUserAccountExist(long userAccountId);
}
