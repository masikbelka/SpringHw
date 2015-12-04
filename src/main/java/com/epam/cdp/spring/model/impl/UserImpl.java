package com.epam.cdp.spring.model.impl;

import com.epam.cdp.spring.model.User;

public class UserImpl implements User{
    private long id;
    private String name;
    private String email;

    public UserImpl(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserImpl(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
