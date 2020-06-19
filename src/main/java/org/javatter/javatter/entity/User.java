package org.javatter.javatter.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
// テーブル名を users に設定
@Table(name = "users")
// スーパークラスであるAbstractEntityを継承
public class User extends LoginUser {
    // primary key
    @Id
    // Auto_increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Not null制約、 文字数 20
    @Column(nullable = false, length = 20)
    private String name;

    // Date型からLocalDate型に変更
    // @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    // private Date birthday;
    private LocalDate birthday;

    @Column(length = 100)
    private String address;

    @Column(length = 500)
    private String introduction;

    private String webSite;

    private String profileImage;

    private String backgroundImage;

    // Not null制約、デフォルト値 1 設定
    @Column(nullable = false, columnDefinition = "bit default 1")
    private Boolean status;

    // スーパークラスAbstractEntityのprePersistメソッドをオーバーライド
    @Override
    public void prePersist() {
        super.prePersist();
        setStatus(true);
    }
}
