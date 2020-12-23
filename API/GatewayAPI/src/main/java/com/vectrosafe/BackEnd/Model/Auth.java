package com.vectrosafe.BackEnd.Model;

public class Auth {
    private Long id_auth;
    private String username;
    private String password;
    public Auth() {}
    public Auth(String username, String passsword) {
        setUsername(username);
        setPassword(passsword);
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

    public Long getId_auth() { return id_auth; }

    public void setId_auth(Long id_auth) {
        this.id_auth = id_auth;
    }
}
