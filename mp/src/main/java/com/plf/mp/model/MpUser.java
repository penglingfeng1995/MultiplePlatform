package com.plf.mp.model;

import lombok.Data;

/**
 * @author plf
 */
@Data
public class MpUser {

    /**
     * 用户id
     */
    private Long id;
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private String cratedAt;

    /**
     * 更新时间
     */
    private String updatedAt;
}
