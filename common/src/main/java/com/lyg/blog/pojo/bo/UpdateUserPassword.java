package com.lyg.blog.pojo.bo;

import com.lyg.blog.pojo.UserBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 接收密码重设信息BO
 * Created by winggonLee on 2020/2/8
 */
public class UpdateUserPassword extends UserBase {
    /**
     * 接收前端配置的新密码
     * 暂存意义
     */
    @Getter
    @Setter
    private String newPassword;
}
