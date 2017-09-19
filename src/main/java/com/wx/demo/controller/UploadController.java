package com.wx.demo.controller;

import com.wx.demo.base.controller.BaseController;
import com.wx.demo.base.dto.JsonResult;
import com.wx.demo.base.entity.FileBean;
import com.wx.demo.base.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangxiong on 2017/3/18.
 */

@Controller
@RequestMapping(value="/api")
public class UploadController extends BaseController {

    @Autowired
    private UploadService uploadService;

    @Value("${project.upload.dir:/data/wwwroot/upload}")
    private String uploadPath;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Object> uploadFile(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        FileBean fileBean = uploadService.saveFile(file);

        return showJsonResult(true, "上传成功", fileBean);
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadImage(@RequestParam(value = "images", required = false) MultipartFile file) throws Exception {
        FileBean fileBean = uploadService.saveFile(file);

        return showJsonResult(true, "上传成功", fileBean);
    }



}


