package com.vectrosafe.Database.Model;

import javax.persistence.*;

@Entity
@Table(name = "auth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_auth;
    private String username;
    private String password;
    public Auth() {}
    public Auth(String username, String passsword) {
        setUsername(username);
        setPassword(passsword);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Auth other = (Auth) obj;
        if (this.getId_auth() != other.getId_auth())
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (this.getId_auth() ^ (this.getId_auth() >>> 32));
        return result;
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


    public Long getId_auth() {
        return id_auth;
    }

    public void setId_auth(Long id_auth) {
        this.id_auth = id_auth;
    }
}
