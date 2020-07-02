package org.javatter.javatter.converter;

import java.time.LocalDate;

import org.javatter.javatter.entity.User;
import org.javatter.javatter.form.UserForm;
import org.javatter.javatter.form.UserUpdateForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private FileEncoder fileEncoder;

    @Autowired
    private FileService fileService;

    // ユーザー登録時のフォームからエンテティへの詰め替え処理
    public void formToEntityAtCreate(UserForm userForm, User user) {
        modelMapper.map(userForm, user);
        user.setBirthday(joinBirthday(userForm));
        user.setEncryptedPassword(encodePassword(userForm));
    }

    // フォームからエンテティへの詰め替え処理
    public void formToEntity(UserUpdateForm userUpdateForm, User user) {
        modelMapper.map(userUpdateForm, user);
        user.setBirthday(joinBirthday(userUpdateForm));
        // フォームで送られてきたファイルを取得
        MultipartFile profImgFile = userUpdateForm.getProfImgFile();
        MultipartFile bgImgFile = userUpdateForm.getBgImgFile();
        // ファイルが空の場合、リターン
        if (!profImgFile.isEmpty()) {
            // フォームからのファイルが存在していてかつDBにファイル名が存在していればサーバーのファイルを削除
            fileService.removeFile(profImgFile, user.getProfileName());
            // 送られてきたファイル一意な名前に変換してエンティティにセット
            String profileName = fileEncoder.getEncodedFileName(profImgFile, user.getId(), "profileName");
            // 生成された一意なファイル名をエンティティにセット
            user.setProfileName(profileName);
            // 生成された一意なファイル名で送られてきたファイルをサーバーに保存
            fileService.uploadFile(profImgFile, profileName);
        }
        // 上記と同様
        if (!bgImgFile.isEmpty()) {
            fileService.removeFile(bgImgFile, user.getBackgroundName());
            String backgroundName = fileEncoder.getEncodedFileName(bgImgFile, user.getId(), "backgroundName");
            user.setBackgroundName(backgroundName);
            fileService.uploadFile(bgImgFile, backgroundName);
        }
    }

    // エンティティからフォームへの詰め替え処理
    public void entityToForm(User user, UserUpdateForm userUpdateForm) {
        modelMapper.map(user, userUpdateForm);
        userUpdateForm.setBirthY(user.getBirthday().getYear());
        userUpdateForm.setBirthM(user.getBirthday().getMonthValue());
        userUpdateForm.setBirthD(user.getBirthday().getDayOfMonth());
    }

    // 生年月日を連結してDateTime型に変換
    private LocalDate joinBirthday(UserForm userForm) {
        int birthY = userForm.getBirthY();
        int birthM = userForm.getBirthM();
        int birthD = userForm.getBirthD();
        return LocalDate.of(birthY, birthM, birthD);
    }

    // 生のパスワードを暗号化
    private String encodePassword(UserForm userForm) {
        String rawPassword = userForm.getRawPassword();
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        return encryptedPassword;
    }
}
