package com.lyg.blog.pojo;

import com.lyg.blog.annotations.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户基础信息
 * Created by winggonLee on 2020/2/6
 */
@Getter
@Setter
public class UserBase implements Serializable {

    private static final long serialVersionUID = 6745137059863288616L;

    /**
     * 登录账号
     */
    @Column(updateKey = true, table = {"t_user","t_user_plus"}, name = "account")
    private String account;

    /**
     * 密码
     */
    @Column(table = "t_user", name = "passWord")
    private String passWord;

    /**
     * 密码加盐
     */
    @Column(table = "t_user", name = "salt")
    private int salt;

}
