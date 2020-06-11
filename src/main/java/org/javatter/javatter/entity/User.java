package org.javatter.javatter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
// テーブル名を users に設定
@Table(name = "users")
// スーパークラスであるAbstractEntityを継承
public class User extends AbstractEntity {
    // primary key
    @Id
    // Auto_increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Not null制約、 文字数 20
    @Column(nullable = false, length = 20)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date birthday;

    @Column(length = 100)
    private String address;

    @Column(length = 500)
    private String introduction;

    private String website;

    private String profileImage;

    private String backgroundImage;

    // ユニークキー設定
    @Column(unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String encryptedPassword;

    // Not null制約、デフォルト値 1 設定
    @Column(nullable = false, columnDefinition = "bit default 1")
    private Boolean status;
}
