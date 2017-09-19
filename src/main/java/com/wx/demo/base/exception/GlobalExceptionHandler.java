package com.wx.demo.base.exception;

import com.wx.demo.base.dto.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult<String> defaultErrorHandler(HttpServletRequest req, Exception  e) throws Exception {
        JsonResult<String> r = new JsonResult<>();
        r.setCode(JsonResult.ERROR);
        r.setMsg(e.getMessage());
        r.setData("Some Error Data");
        return r;
    }
}
