package org.javatter.javatter.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.javatter.javatter.validation.Unique;

import lombok.Data;

@Data
public class UserForm {
    @Size(min = 1, max = 20)
    private String name;

    @Min(value = 1940)
    @Max(value = 2010)
    private int birthY;

    @Min(value = 1)
    @Max(value = 12)
    private int birthM;

    @Min(value = 1)
    @Max(value = 31)
    private int birthD;

    @Size(min = 1, max = 255)
    @Unique
    @Email
    private String email;

    @Size(min = 6, max = 20)
    private String rawPassword;

    // 権限を管理するカラムを定義（データは文字列のカンマ区切りで格納する）
    private String[] roles;
}
