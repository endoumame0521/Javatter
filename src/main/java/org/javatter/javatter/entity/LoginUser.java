package org.javatter.javatter.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginUser extends AbstractEntity {
    // ユニークキー設定
    @Column(nullable = false, unique = true)
    private String email;

    // BCrypt(Blowfish暗号)で暗号化すると60文字以内になるので length=60に設定
    @Column(nullable = false, length = 60, unique = true)
    private String encryptedPassword;
}