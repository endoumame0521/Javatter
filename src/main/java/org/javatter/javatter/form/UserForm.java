package org.javatter.javatter.form;

import lombok.Data;

@Data
public class UserForm {
    private String name;
    private int birthYear;
    private int birthMonth;
    private int birthDay;
    private String email;
    private String encryptedPassword;
}
