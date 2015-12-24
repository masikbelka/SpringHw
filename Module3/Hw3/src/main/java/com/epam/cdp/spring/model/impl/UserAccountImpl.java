package com.epam.cdp.spring.model.impl;

import com.epam.cdp.spring.model.UserAccount;

public class UserAccountImpl implements UserAccount {
    private long userAccountId;
    private long userId;
    private double prepaidMoney;

    @Override
    public long getUserAccountId() {
        return userAccountId;
    }

    @Override
    public void setUserAccountId(long userAccountId) {
        this.userAccountId = userAccountId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public double getPrepaidMoney() {
        return prepaidMoney;
    }

    @Override
    public void setPrepaidMoney(double prepaidMoney) {
        this.prepaidMoney = prepaidMoney;
    }

    @Override
    public String toString() {
        return "UserAccountImpl{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", prepaidMoney=" + prepaidMoney +
                '}';
    }
}