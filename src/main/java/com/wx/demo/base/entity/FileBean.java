package com.wx.demo.base.entity;

import java.io.Serializable;

/**
 * Created by wangxiong on 2017/3/18.
 */
public class FileBean implements Serializable {
    private static final long serialVersionUID = 8656597559014685635L;

    private String type ;     //文件类型
    private long size ;       //文件大小
    private String ext;       //文件后缀
    private String path;      //文件路径
    private String oriFileName;   //原先文件名称
    private String saveFileName;  //生成文件名称
    private String mimeType;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOriFileName() {
        return oriFileName;
    }

    public void setOriFileName(String oriFileName) {
        this.oriFileName = oriFileName;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }
}
