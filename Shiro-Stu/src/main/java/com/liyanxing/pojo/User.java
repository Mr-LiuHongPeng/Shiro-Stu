package com.liyanxing.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private String per;
    private String salt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", password='" + password + '\'' + ", per='" + per + '\'' + '}';
    }
}