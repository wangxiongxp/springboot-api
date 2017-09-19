package com.wx.demo.base.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * 文件上传
 * Created by wangxiong on 2017/3/19.
 */
public class FileUploadUtils {

    //文件上传目标目录
    private String destPath = null;

    //新文件名称，不设置时默认为原文件名
    private String newFileName = null;

    private static final String SAVE_PATH = "/upload";
    private static final String URL = "/image/";

    public static String restore(MultipartFile multipartFile) {
        String url = "";
        try {
            if (multipartFile.isEmpty() == true) {
                return url;
            }

            String originalFileName = multipartFile.getOriginalFilename();
            String extName = originalFileName.substring(originalFileName.lastIndexOf('.')+1, originalFileName.length());
            String saveFileName = generateSaveFileName(extName);
            Long fileSize = multipartFile.getSize();

            System.out.println("###" + originalFileName);
            System.out.println("###" + extName);
            System.out.println("###" + saveFileName);
            System.out.println("###" + fileSize);

            writeFile(multipartFile, saveFileName);

            url = URL + saveFileName;
        } catch (IOException ex) {
            throw new RuntimeException("upload file");
        }
        return url;
    }

    private static void writeFile(MultipartFile multipartiFile, String saveFileName) throws IOException {
        byte[] fileData = multipartiFile.getBytes();
        FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
        fos.write(fileData);
        fos.close();
    }

    private static String generateSaveFileName(String extName) {
        String fileName = "";
        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH)+1;
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR_OF_DAY);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);
        fileName += ("." + extName);
        return fileName;
    }


}