package com.codeplayer.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -7242675127532392768L;

    /**
     *主键ID
     */
    @Id
    private Long userId;
    /**
     * 作者昵称
     */
    private String name;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 自定义token
     */
    private String token;
    /**
     * 头像url
     */
    private String avatarUrl;
    /**
     * 性别：男、女、保密
     */
    private String sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 出生年月日
     */

    private String birthday;
    /**
     * github账号id
     */
    private String githubAccountId;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
