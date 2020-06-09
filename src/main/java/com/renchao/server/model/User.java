package com.renchao.server.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class User implements Serializable {
    String id;
    String name;
    String password;
    String email;
    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //    这里密码没有加密
    @Override
    public String toString() {
        return "User详细参数 id=" + id + ",name=" + name + ",password=" + password + ",email=" + email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
