package org.javatter.javatter.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

// Entityクラスのスーパークラスを宣言
@MappedSuperclass
@Data
public abstract class AbstractEntity {
    // Date型からLocalDateTime型に変更
    // @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Date型からLocalDateTime型に変更
    // @Temporal(TemporalType.TIMESTAMP)
    // @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT
    // CURRENT_TIMESTAMP")
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // データベースにレコード登録時、登録日時と更新日時を現在時刻にする
    @PrePersist
    public void prePersist() {
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    // MySQL側 に TIMESTAMP DEFAULT CURRENT_TIMESTAMPがあれば不要
    // データベースにレコード更新時、更新日時を現在時刻にする
    @PreUpdate
    public void preUpdate() {
        setUpdatedAt(LocalDateTime.now());
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
