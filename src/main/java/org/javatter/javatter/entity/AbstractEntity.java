package org.javatter.javatter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

// Entityクラスのスーパークラスを宣言
@MappedSuperclass
// Getterを宣言
@Getter
// Setterを宣言
@Setter
public class AbstractEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    // @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT
    // CURRENT_TIMESTAMP")
    @Column(nullable = false)
    private Date updatedAt;

    // データベースにレコード登録時、登録日時と更新日時を現在時刻にする
    @PrePersist
    public void prePersist() {
        setCreatedAt(new Date());
        setUpdatedAt(new Date());
    }

    // MySQL側 に TIMESTAMP DEFAULT CURRENT_TIMESTAMPがあれば不要
    // データベースにレコード更新時、更新日時を現在時刻にする
    @PreUpdate
    public void preUpdate() {
        setUpdatedAt(new Date());
    }
}

// 日付項目をマッピングする場合は、 @Temporal でフィールドをアノテートする
// @Temporal の値には、データベース上の詳細な型（DATE, TIME, TIMESTAMP）を指定する
// DATE 日付だけ
// TIME 時刻だけ
// TIMESTAMP 日付と時刻の両方

// アノテーション 備考
// @PrePersist 永続化前に実行
// @PostPersist 永続化後に実行
// @PreUpdate 更新処理前に実行
// @PostUpdate 更新処理後に実行
// @PreRemove 削除前に実行
// @PostRemove 削除後に実行
// @PreLoad 読み込み前に実行
// @PostLoad 読み込み後に実行
