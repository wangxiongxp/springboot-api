package com.wx.demo.base.controller;

import com.wx.demo.base.dto.JsonResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    /**
     * 返回json格式数据
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public JsonResult<Object> showJsonResult(boolean code, String msg, Object data){
        JsonResult result = new JsonResult();
        if(code){
            result.setCode(JsonResult.SUCCESS);
        }else{
            result.setCode(JsonResult.ERROR);
        }
        result.setMsg(msg);
        result.setData(data);

        return result;
    }

    /**
     * 构建查询参数
     * @param request
     * @return
     */
    public Map<String,Object> buildSearchParam(HttpServletRequest request){

        //获取客户端需要那一列排序
        String orderColumn = request.getParameter("column");
        //获取排序方式 默认为asc
        String orderDir  = request.getParameter("dir");
        //获取查询关键字
        String keyword   = request.getParameter("keyword");

        Integer pageNum  = getPageNum(request);
        Integer pageSize = getPageSize(request);

        Map<String, Object> map = new HashMap<>();

        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        map.put("keyword",keyword);
        if(StringUtils.isNotBlank(orderColumn)){
            map.put("orderBy",orderColumn + " " + orderDir);
        }

        return map;
    }

    /**
     * 获得pager.offset参数的值
     *
     * @param request
     * @return
     */
    protected int getPageNum(HttpServletRequest request) {
        int pageNum = 1;
        try {
            String page = request.getParameter("page");
            if (null != page ) {
                pageNum  = Integer.parseInt(page) ;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return pageNum;
    }

    /**
     * 设置默认每页大小
     *
     * @return
     */
    protected int getPageSize(HttpServletRequest request) {
        int pageSize = 10;    // 默认每页10条记录
        try {
            String pageSizes = request.getParameter("length");
            if (null != pageSizes ) {
                pageSize = Integer.parseInt(pageSizes);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return pageSize;
    }
}
