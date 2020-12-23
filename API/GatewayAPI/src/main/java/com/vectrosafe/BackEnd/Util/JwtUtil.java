package com.vectrosafe.BackEnd.Util;

import com.vectrosafe.BackEnd.Model.Auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private String secret = "akucintakamutapikamucintadiaohnooo";

    public Auth parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            Auth u = new Auth();
            u.setUsername(body.getSubject());
            u.setId_auth(Long.parseLong((String) body.get("userId")));

            return u;

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public String generateToken(Auth u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getId_auth() + "");
        Date date = new Date();
        long t = date.getTime();
        Date expirationTime = new Date(t + 1800000l); // set 30 mins
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(expirationTime)
                .compact();
    }
}
