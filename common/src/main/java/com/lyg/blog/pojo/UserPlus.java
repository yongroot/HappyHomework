package com.lyg.blog.pojo;

import com.lyg.blog.annotations.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用户个人资料
 * Created by winggonLee on 2020/2/6
 */
@Getter
@Setter
public class UserPlus extends UserBase implements Serializable {

    private static final long serialVersionUID = 6745137059863288607L;

    /**
     * 用户名
     */
    @Column(table = "t_user_plus")
    private String userName;

    /**
     * 头像地址
     */
    @Column(table = "t_user_plus", name = "headUrl")
    private String url;

    /**
     * 生日
     */
    @Column(table = "t_user_plus")
    private LocalDate birthday;

    /**
     * 个人描述
     */
    @Column(table = "t_user_plus")
    private String introduce;

    /**
     * 性别
     * 0男1女
     */
    @Column(table = "t_user_plus")
    private int sex;
}
