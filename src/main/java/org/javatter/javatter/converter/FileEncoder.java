package org.javatter.javatter.converter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileEncoder {
    // 取得したファイルをバイナリ（バイト配列）に変換するメソッドを定義
    private byte[] getFileEncodedByBytes(File file) {
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // バイナリに変換したファイルをBase64でエンコード
    public String encodeFileByBase64(File file) {
        // ファイルがなければリターン
        if (file == null)
            return null;
        // ファイルをBase64形式でエンコードしてリターン
        return Base64.getEncoder().encodeToString(getFileEncodedByBytes(file));
    }

    // ファイルとidとフィールド名の3つから一意なファイル名を生成する
    public String getEncodedFileName(MultipartFile file, Long id, String columnName) {
        if (file.isEmpty())
            return null;

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // ３つのバイト配列を連結
            outputStream.write(file.getBytes()); // 対象ファイルのbyte配列を追加
            outputStream.write(String.valueOf(id).getBytes()); // 対象idのbyte配列を追加
            outputStream.write(columnName.getBytes()); // 対象フィールド名のbyte配列を追加
            // SHA-1に変換
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] sha1_result = sha1.digest(outputStream.toByteArray());
            return String.format("%040x", new BigInteger(1, sha1_result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
