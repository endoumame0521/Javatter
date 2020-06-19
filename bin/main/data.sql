
insert into users (
    created_at,
    updated_at,
    address,
    birthday,
    email,
    encrypted_password,
    introduction,
    name,
    profile_image,
    background_image,
    web_site
) value (
    now(),
    now(),
    '兵庫県',
    '1992-12-28',
    'test1@sample1.com',
    'aaaaaa',
    'こんにちは！',
    '田中太郎',
    'test.jpg',
    'sample.jpg',
    'http://sample1.com'
), (
    now(),
    now(),
    '大阪府',
    '1981-10-21',
    'test2@sample2.com',
    'bbbbbb',
    'はじめまして！',
    '鈴木一郎',
    'test.jpg',
    'sample.jpg',
    'http://sample2.com'
), (
    now(),
    now(),
    '京都府',
    '1965-07-14',
    'test3@sample3.com',
    'cccccc',
    '宜しくお願い致します！',
    '大原ひろし',
    'test.jpg',
    'sample.jpg',
    'http://sample3.com'
), (
    now(),
    now(),
    '奈良県',
    '2001-09-09',
    'test4@sample4.com',
    'dddddd',
    'こんばんは！',
    '大杉謙信',
    'test.jpg',
    'sample.jpg',
    'http://sample4.com'
), (
    now(),
    now(),
    '和歌山県',
    '1978-04-04',
    'test5@sample5.com',
    'eeeeee',
    '私は誰ですか？',
    '山田花子',
    'test.jpg',
    'sample.jpg',
    'http://sample5.com'
);

-- セッション用のテーブルを作成
CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;