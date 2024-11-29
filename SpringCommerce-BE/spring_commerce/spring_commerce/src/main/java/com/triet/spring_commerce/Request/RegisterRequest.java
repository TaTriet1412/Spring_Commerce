package com.triet.spring_commerce.Request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotEmpty(message = "Email không được để trống")
    private String email;

    @NotEmpty(message = "Mật khẩu không được để trống")
    private String password;

    @NotEmpty(message = "Số điện thoại không được để trống")
    private String phone;

    @NotEmpty(message = "Tên không được để trống")
    private String name;

    @NotNull(message = "Chưa có giới tính")
    private boolean gender;
}
