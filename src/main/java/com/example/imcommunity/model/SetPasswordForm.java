package com.example.imcommunity.model;

import lombok.Data;

@Data
public class SetPasswordForm {
    private Long userId;
    private String password;
    private String passwordOld;
    private String passwordRepeated;
}
