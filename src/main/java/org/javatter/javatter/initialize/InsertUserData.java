package org.javatter.javatter.initialize;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.javatter.javatter.entity.User;
import org.javatter.javatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// DBへの初期データ投入
public class InsertUserData {
        @Autowired
        UserRepository repository;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @PostConstruct
        public void insertUser() {
                createUser("aaaaaa", LocalDate.of(1940, 1, 1), "a@a", encodePassword("aaaaaa"), "大阪府",
                                "はじめまして！宜しくお願い致します！", "http://sample1.com", "test1.jpg", "sample1.jpg");
                createUser("bbbbbb", LocalDate.of(1945, 2, 2), "b@b", encodePassword("bbbbbb"), "兵庫県", "こんにちは！お元気ですか？",
                                "http://sample2.com", "test2.jpg", "sample2.jpg");
                createUser("cccccc", LocalDate.of(1950, 3, 3), "c@c", encodePassword("cccccc"), "京都府",
                                "まだよく分からないですが宜しくお願い致します！", "http://sample3.com", "test3.jpg", "sample3.jpg");
                createUser("dddddd", LocalDate.of(1955, 4, 4), "d@d", encodePassword("dddddd"), "滋賀県",
                                "今日という日を迎えることが出来て私はとても幸せです！", "http://sample4.com", "test4.jpg", "sample4.jpg");
                createUser("eeeeee", LocalDate.of(1960, 5, 5), "e@e", encodePassword("eeeeee"), "奈良県",
                                "見てくださってありがとうございます！お手柔らかに！", "http://sample5.com", "test5.jpg", "sample5.jpg");
                createUser("ffffff", LocalDate.of(1965, 6, 6), "f@f", encodePassword("ffffff"), "和歌山県",
                                "こんにちは！今日もいい天気ですね！", "http://sample6.com", "test6.jpg", "sample6.jpg");
                createUser("gggggg", LocalDate.of(1970, 7, 7), "g@g", encodePassword("gggggg"), "三重県",
                                "最近は睡眠が浅くなってきました！良い熟睡方法があれば教えてください！", "http://sample7.com", "test7.jpg", "sample7.jpg");
                createUser("hhhhhh", LocalDate.of(1975, 8, 8), "h@h", encodePassword("hhhhhh"), "岡山県",
                                "なんで節分では鬼を追い払うかご存知ですか？", "http://sample8.com", "test8.jpg", "sample8.jpg");
                createUser("iiiiii", LocalDate.of(1980, 9, 9), "i@i", encodePassword("iiiiii"), "鳥取県",
                                "こんなにも頑張っているのに報われないのはどうしてですか？", "http://sample9.com", "test9.jpg", "sample9.jpg");
                createUser("jjjjjj", LocalDate.of(1985, 10, 10), "j@j", encodePassword("jjjjjj"), "島根県",
                                "海遊館では様々な海洋生物を見ることが出来ます！天保山へ遊びに来た際には是非お立ち寄りください！", "http://sample10.com", "test10.jpg",
                                "sample10.jpg");
                createUser("kkkkkk", LocalDate.of(1990, 11, 11), "k@k", encodePassword("kkkkkk"), "広島県",
                                "フェリーで北海道まで行けるってご存知でしたか？私は知らなかったです！いつか行ってみたいな！", "http://sample11.com", "test11.jpg",
                                "sample11.jpg");
                createUser("llllll", LocalDate.of(1995, 12, 12), "l@l", encodePassword("llllll"), "徳島県",
                                "また夜遊びをしていますね！ほんと良い歳なんだから程々にしてくださいよ！", "http://sample12.com", "test12.jpg",
                                "sample12.jpg");
        }

        // ユーザーをDBへ登録する関数を定義
        private void createUser(String name, LocalDate birthday, String email, String encryptedPassword, String address,
                        String introduction, String webSite, String profileImage, String backgroundImage) {
                User user = new User();
                user.setName(name);
                user.setBirthday(birthday);
                user.setEmail(email);
                user.setEncryptedPassword(encryptedPassword);
                user.setAddress(address);
                user.setIntroduction(introduction);
                user.setWebSite(webSite);
                // user.setProfileImage(profileImage);
                // user.setBackgroundImage(backgroundImage);
                this.repository.save(user);
        }

        // 生のパスワードを暗号化
        private String encodePassword(String rawPassword) {
                String encryptedPassword = passwordEncoder.encode(rawPassword);
                return encryptedPassword;
        }
}
