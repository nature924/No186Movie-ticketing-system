package com.example.cinema.vo;

import com.example.cinema.po.User;

/**
 * @author fjj
 * @date 2019/4/11 3:22 PM
 */
public class UserVO {
    private Integer id;
    private String username;
    private String password;
    private Integer kind;

    public UserVO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.kind=user.getKind();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer id) {
        this.kind = id;
    }
}
