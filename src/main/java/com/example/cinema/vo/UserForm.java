package com.example.cinema.vo;

/**
 * @author huwen
 * @date 2019/3/23
 */
public class UserForm {
    /**
     * 用户名，不可重复
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    private int kind;

    public int getKind(){return kind;}

    public void setKind(int i){this.kind=i;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
