
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
