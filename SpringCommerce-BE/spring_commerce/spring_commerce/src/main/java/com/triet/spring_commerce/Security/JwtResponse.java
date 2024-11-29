package com.triet.spring_commerce.Security;

import com.triet.spring_commerce.Entity.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JwtResponse {

    private String token;
    private User user;

    public JwtResponse(String token,User user) {
        this.token = token;
        this.user = user;
    }
}
