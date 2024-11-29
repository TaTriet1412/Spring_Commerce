package com.triet.spring_commerce.Request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotEmpty(message = "Email không được để trống")
    private String email;


    @NotEmpty(message = "Mật khẩu không được để trống")
    private String password;
}
