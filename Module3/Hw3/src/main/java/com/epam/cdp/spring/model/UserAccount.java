package com.epam.cdp.spring.model;

/**
 * Created by Yurii Chukhlatyi
 */
public interface UserAccount {
    long getUserAccountId();

    void setUserAccountId(long userAccountId);

    long getUserId();

    void setUserId(long userId);

    double getPrepaidMoney();

    void setPrepaidMoney(double prepaidMoney);
}
