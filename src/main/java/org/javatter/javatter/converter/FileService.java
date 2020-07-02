package org.javatter.javatter.converter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    FileEncoder fileEncoder;

    // @formatter:off
    // ファイルをアップロードするパスを設定
    private static final String UPLOAD_FILE_PATH = (new StringBuffer("src")
        .append(File.separator)
        .append("main").append(File.separator)
        .append("resources").append(File.separator)
        .append("static").append(File.separator)
        .append("images").append(File.separator)
        .append("uploads").append(File.separator)).toString();
    // @formatter:on

    // @formatter:off
    // アップロードされたファイルのリソースパスを設定
    private static final String UPLOADED_RESOURCE_PATH = (new StringBuffer("classpath:")
        .append("static").append(File.separator)
        .append("images").append(File.separator)
        .append("uploads").append(File.separator)).toString();
    // @formatter:on

    // ファイルの拡張子を設定
    private static final String fileExtension = ".jpg";

    // アップロードされたファイルのパスを取得
    private String getUploadFilePath(String fileName) {
        return UPLOAD_FILE_PATH + fileName + fileExtension;
    }

    // アップロードされるorされたファイルのリソースパスを取得
    private String getUploadResourcePath(String fileName) {
        return UPLOADED_RESOURCE_PATH + fileName + fileExtension;
    }

    // ファイル名からファイルのリソースを取得するメソッドを定義
    private Resource getFileResource(String fileName) {
        String fileResource = getUploadResourcePath(fileName);
        return resourceLoader.getResource(fileResource);
    }

    // ファイル名からファイルを取得するメソッドを定義
    private File getFileByFileName(String fileName) {
        try {
            return getFileResource(fileName).getFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // MultipartFileをFileへコンバート
    public File convertToFile(MultipartFile file) {
        try {
            File convertFile = new File(file.getOriginalFilename());
            convertFile.createNewFile();
            try (FileOutputStream outputStream = new FileOutputStream(convertFile)) {
                outputStream.write(file.getBytes());
                return convertFile;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ファイル名を元にサーバーからファイルを取得してBase64形式でエンコード
    public String getFileEncodedByBase64(String fileName) {
        // DBにファイル名が無い場合（初回アップロードの場合）はリターン
        if (fileName == null)
            return null;
        // ファイル名を元にサーバーに保存されたファイルを取得
        File file = getFileByFileName(fileName);
        // 取得したファイルをBase64形式でエンコードする
        return fileEncoder.encodeFileByBase64(file);
    }

    // ファイルをサーバーにアップロードする
    public void uploadFile(MultipartFile file, String fileName) {
        String filePath = getUploadFilePath(fileName);
        // @formatter:off
        try (BufferedOutputStream outputStream = new BufferedOutputStream(
            new FileOutputStream(filePath))) {
            outputStream.write(file.getBytes());
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // @formatter:on

        // ファイルのアップロードが完了するまで待つ（約60秒を越えれば強制的に続行する）
        try {
            int i = 0;
            while (getFileByFileName(fileName) == null) {
                Thread.sleep(1000);

                if (i > 60) {
                    System.out.println("タイムオーバーです。画面遷移します。");
                    break;
                }
                System.out.println("ファイルの書き込み中です....................");
                i++;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ファイルを削除するメソッドを定義
    public void removeFile(MultipartFile file, String fileName) {
        // DBにファイル名が無い場合（初回アップロードの場合）はリターン
        if (fileName == null) {
            return;
        }
        // 削除するファイルを設定
        File targetFile = new File(getUploadFilePath(fileName));
        // ファイル削除が成功するまで60回（インターバル1s）削除を繰り返す
        for (int j = 0; j < 60; j++) {
            if (targetFile.delete()) {
                System.out.println("ファイルの削除に成功しました");
                break;
            } else {
                System.out.println("ファイルの削除に失敗しました");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
