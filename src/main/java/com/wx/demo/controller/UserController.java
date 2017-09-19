package com.wx.demo.controller;

import com.wx.demo.base.controller.BaseController;
import com.wx.demo.base.dto.JsonResult;
import com.wx.demo.base.entity.PageBean;
import com.wx.demo.base.mapper.JsonMapper;
import com.wx.demo.base.utils.DateUtils;
import com.wx.demo.entity.User;
import com.wx.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "User")
@RestController
@RequestMapping(value="/user")
public class UserController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * @return
     */
    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @RequestMapping(value="/getUserList", method = RequestMethod.GET)
    public List<User> getUserList() {
        return userService.listAll();
    }

    /**
     * 获取用户列表带分页
     * @return
     */
    @ApiOperation(value="获取用户列表带分页", notes="获取用户列表带分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前第几页", paramType ="query", dataType = "int")
    })
    @RequestMapping(value="/getUserListByPage", method = RequestMethod.GET)
    public JsonResult getUserListByPage(HttpServletRequest request) {

        Map<String,Object> map = buildSearchParam(request);
        PageBean<User> pageInfo = userService.listByPage(map);

        return showJsonResult(true,"查询成功",pageInfo);
    }

    /**
     * 创建用户
     * @return
     */
    // paramType 有五个可选值 ： path, query, body, header, form
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户姓名", paramType ="query",required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "用户年龄", paramType ="query", required = true, dataType = "int")
    })
    @RequestMapping(value="/saveUser", method=RequestMethod.POST)
    public JsonResult saveUser(@RequestParam("userName")  String userName,
                           @RequestParam("age")  Integer age) {

        User user = new User();
        user.setUserName(userName);
        user.setAge(age);
        user.setCreateTime(DateUtils.getDateTime());
//        user.setCreateBy();
        userService.save(user);

        return showJsonResult(true,"保存成功",null);
    }

    /**
     * 获取用户信息
     * @return
     */
    @ApiOperation(value="获取用户详细信息", notes="根据id来获取用户详细信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", paramType ="path",required = true, dataType = "Long")
    @RequestMapping(value="/getUserById/{userId}", method=RequestMethod.GET)
    public JsonResult getUserById(@PathVariable Long userId) {

        User user = userService.getById(userId);

        return showJsonResult(true,"查询成功",user) ;
    }

    /**
     * 更新用户
     * @return
     */
    @ApiOperation(value="更新用户详细信息", notes="根据id指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType ="path",required = true, dataType = "Long"),
            @ApiImplicitParam(name = "userName", value = "用户姓名", paramType ="query",required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", paramType ="query",required = true, dataType = "int")
    })
    @RequestMapping(value="/updateUser/{userId}", method=RequestMethod.PUT)
    public JsonResult updateUser(@PathVariable Long userId,
                          @RequestParam  String userName,
                          @RequestParam  Integer age) {

        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setAge(age);
        user.setUpdateTime(DateUtils.getDateTime());
        userService.update(user);

        return showJsonResult(true,"更新成功",null) ;
    }

    /**
     * 删除用户
     * @return
     */
    @ApiOperation(value="删除用户", notes="根据id来删除指定对象")
    @ApiImplicitParam(name = "userId", value = "用户ID", paramType ="path",required = true, dataType = "Long")
    @RequestMapping(value="/removeUser/{userId}", method=RequestMethod.DELETE)
    public JsonResult<Object> removeUser(@PathVariable Long userId) {

        userService.remove(userId);

        return showJsonResult(true,"删除成功",null) ;
    }

}
