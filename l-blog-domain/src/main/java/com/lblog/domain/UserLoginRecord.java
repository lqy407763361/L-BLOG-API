package com.lblog.domain;

public class UserLoginRecord {
    private Long id;

    private Long user_id;

    private String login_ip;

    private Long login_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getLogin_ip() {
        return login_ip;
    }

    public void setLogin_ip(String login_ip) {
        this.login_ip = login_ip;
    }

    public Long getLogin_time() {
        return login_time;
    }

    public void setLogin_time(Long login_time) {
        this.login_time = login_time;
    }
}
