package com.example.imcommunity.model;

import com.example.imcommunity.validator.NotContainBlank;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserFrom {
    private String username;
    private String password;
}
