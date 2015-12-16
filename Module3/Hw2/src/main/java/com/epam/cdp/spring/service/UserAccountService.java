package com.epam.cdp.spring.service;

import com.epam.cdp.spring.model.UserAccount;

/**
 * Created by Yurii Chukhlatyi
 */
public interface UserAccountService {
    UserAccount create(UserAccount userAccount);

    boolean delete(long userAccountId);

    boolean deleteByUser(long userId);

    UserAccount getUserAccountById(long userAccountId);

    UserAccount getUserAccountByUserId(long userId);

    boolean update(UserAccount userAccount);

    boolean isUserAccountExist(long userAccountId);

    boolean isUserAccountForUserExist(long userId);
}
