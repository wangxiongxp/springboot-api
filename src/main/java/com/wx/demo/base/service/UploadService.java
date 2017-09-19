package com.wx.demo.base.service;

import com.wx.demo.base.entity.FileBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Random;

/**
 * 文件上传
 * Created by wangxiong on 2017/3/19.
 */
@Service
public class UploadService {

    @Value("${project.upload.dir:/data/wwwroot/upload}")
    private String uploadDir;


    public FileBean saveHandlingArchCatalogFile(String archHId, MultipartFile uploadfile) {
        String realPath = "/handlingArchives/" + archHId + "/catalog";
        String uploadPath = uploadDir + realPath;
        return saveFile(uploadfile, uploadPath, "/upload" + realPath);
    }

    public FileBean saveFile(MultipartFile uploadfile, String uploadPath, String prefix) {
        FileBean fileBean = new FileBean();
        try {
            if (uploadfile.isEmpty()) {
                return fileBean;
            }
            /**
             * 文件目录是否存在，不存在新建一个目录
             */
            File dir = new File(uploadPath);
            if(!dir.exists()){
                dir.mkdirs();
            }

            String originalFileName = uploadfile.getOriginalFilename();
            String extName = originalFileName.substring(originalFileName.lastIndexOf('.')+1, originalFileName.length());
            String saveFileName = generateSaveFileName(extName);
            Long fileSize = uploadfile.getSize();

            System.out.println("###" + originalFileName);
            System.out.println("###" + extName);
            System.out.println("###" + saveFileName);
            System.out.println("###" + fileSize);

            String filepath = Paths.get(uploadPath, saveFileName).toString();

            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();

            fileBean.setOriFileName(originalFileName);
            fileBean.setSaveFileName(saveFileName);
            fileBean.setExt(extName);
            fileBean.setSize(fileSize);
            fileBean.setPath(prefix + "/" + saveFileName);
            fileBean.setMimeType(uploadfile.getContentType());
            //url = URL + saveFileName;
        } catch (IOException ex) {
            throw new RuntimeException("upload file");
        }
        return fileBean;
    }

    public FileBean saveFile(MultipartFile uploadfile) {
        return saveFile(uploadfile, uploadDir, "/upload");
    }

    private String generateSaveFileName(String extName) {
        String fileName = "";
        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH)+1;
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR_OF_DAY);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);
        Random random =new Random();
        fileName += random.nextInt(9999);
        fileName += ("." + extName);
        return fileName;
    }
}
