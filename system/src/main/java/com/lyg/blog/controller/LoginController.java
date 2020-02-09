package com.lyg.blog.controller;

import com.github.pagehelper.Page;
import com.lyg.blog.annotations.Log;
import com.lyg.blog.pojo.UserBase;
import com.lyg.blog.pojo.UserPlus;
import com.lyg.blog.pojo.bo.UpdateUserPassword;
import com.lyg.blog.pojo.vo.ResponseDomain;
import com.lyg.blog.service.UserBaseService;
import com.lyg.blog.service.UserPlusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by winggonLee on 2020/2/6
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private UserPlusService userPlusService;

    /**
     * 注册入口
     */
    @Transactional
    @Log(value = "用户注册", save = true)
    @PostMapping("/register")
    public ResponseDomain register(@RequestBody UserPlus userPlus) {
        ResponseDomain<UserPlus> result = new ResponseDomain<>();
        if (userBaseService.insert(userPlus) && userPlusService.insert(userPlus)) {
            result.setSuccess();
        } else {
            result.setMessage("注册失败");
        }
        return result.setData(userPlus);
    }

    /**
     * 登录入口
     */
    @Log(value = "用户登录", save = true)
    @PostMapping("/loginIn")
    public ResponseDomain loginIn(@RequestBody UserBase user) {
        ResponseDomain<String> result = new ResponseDomain<>();
        String token = userBaseService.loginIn(user, false);
        if (token != null) {
            result.setSuccess().setData(token);
        } else {
            result.setMessage("账号或密码错误");
        }
        return result;
    }

    /**
     * 更新账号信息
     */
    @Log(value = "更新信息", save = true)
    @PostMapping("/update")
    public ResponseDomain update(@RequestBody UserPlus userPlus) {
        ResponseDomain result = new ResponseDomain<>();
        if (userPlusService.update(userPlus)) result.setSuccess();
        return result;
    }

    /**
     * 重设密码入口
     * 新老密码对比
     */
    @Log(value = "更新密码", save = true)
    @PostMapping("/updatePassWord")
    public ResponseDomain updatePassWord(@RequestBody UpdateUserPassword updateUser) {
        ResponseDomain result = new ResponseDomain<>();
        if (userBaseService.updatePassWord(updateUser)) result.setSuccess();
        return result;
    }

    /**
     * 获取个人信息
     */
    @PostMapping("/getInfo")
    public ResponseDomain getInfo(@RequestBody UserPlus userPlus) {
        return new ResponseDomain<>().setData(userPlusService.getOne(userPlus)).setSuccess();
    }

    /**
     * 注销
     */
    @PostMapping("/loginOut")
    public void loginOut(String account) {
        //todo 从cookie获取token
        String token = "";
        userBaseService.loginOut(account, token);
    }
}
