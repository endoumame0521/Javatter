package org.javatter.javatter.form;

import lombok.Data;

@Data
public class UserForm {
    private String name;
    private int birthY;
    private int birthM;
    private int birthD;
    private String email;
    private String encryptedPassword;
}
