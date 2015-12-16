package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.dao.UserAccountDAO;
import com.epam.cdp.spring.model.UserAccount;
import com.epam.cdp.spring.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yurii Chukhlatyi
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {
    private static final Logger LOGGER = LogManager.getLogger(UserAccountServiceImpl.class);

    @Autowired
    private UserAccountDAO userAccountDAO;

    @Override
    public UserAccount create(UserAccount userAccount) {
        UserAccount createdUserAccount = null;
        if (isValid(userAccount)) {
            createdUserAccount = userAccountDAO.create(userAccount);
        } else {
            LOGGER.error("Invalid user account " + String.valueOf(userAccount));
        }
        return createdUserAccount;
    }

    @Override
    public boolean delete(long userAccountId) {
        boolean isSuccessfullyDeleted = false;
        if (userAccountId > 0) {
            isSuccessfullyDeleted = userAccountDAO.deleteUserAccountById(userAccountId);
        } else {
            LOGGER.error("Invalid user account id " + userAccountId);
        }
        return isSuccessfullyDeleted;
    }

    @Override
    public boolean deleteByUser(long userId) {
        boolean isSuccessfullyDeleted = false;
        if (userId > 0) {
            isSuccessfullyDeleted = userAccountDAO.deleteUserAccountByUser(userId);
        } else {
            LOGGER.error("Invalid user id " + userId);
        }
        return isSuccessfullyDeleted;
    }

    @Override
    public UserAccount getUserAccountById(long userAccountId) {
        UserAccount userAccount = null;
        if (userAccountId > 0) {
            userAccount = userAccountDAO.getUserAccountById(userAccountId);
        } else {
            LOGGER.error("Invalid user account id " + userAccountId);
        }
        return userAccount;
    }

    @Override
    public UserAccount getUserAccountByUserId(long userId) {
        UserAccount userAccount = null;
        if (userId > 0) {
            userAccount = userAccountDAO.getUserAccountByUserId(userId);
        } else {
            LOGGER.error("Invalid user id " + userId);
        }
        return userAccount;
    }

    @Override
    public boolean update(UserAccount userAccount) {
        boolean isUpdatedSuccessfully = false;
        if (isValid(userAccount) && userAccount.getUserAccountId() > 0) {
            isUpdatedSuccessfully = userAccountDAO.update(userAccount);
        } else {
            LOGGER.error("Invalid user account " + String.valueOf(userAccount));
        }
        return isUpdatedSuccessfully;
    }

    @Override
    public boolean isUserAccountExist(long userAccountId) {
        boolean isUserAccountExist = false;
        if (userAccountId > 0) {
            isUserAccountExist = userAccountDAO.isUserAccountExist(userAccountId);
        }else {
            LOGGER.error("Invalid user account id " + userAccountId);
        }
        return isUserAccountExist;
    }

    @Override
    public boolean isUserAccountForUserExist(long userId) {
        boolean isUserAccountExist = false;
        if (userId > 0) {
            isUserAccountExist = userAccountDAO.isUserAccountForUserExist(userId);
        }else {
            LOGGER.error("Invalid user account id " + userId);
        }
        return isUserAccountExist;
    }


    private boolean isValid(UserAccount userAccount) {
        return userAccount != null && userAccount.getUserId() > 0 && userAccount.getPrepaidMoney() >= 0;
    }
}
