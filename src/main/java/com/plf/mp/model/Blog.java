package com.plf.mp.model;

import lombok.Data;

/**
 * @author plf
 */
@Data
public class Blog {
    /**
     * id
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 简介
     */
    private String description;
    /**
     * 是否展示到首页
     */
    private Boolean atIndex;
    /**
     * 所属用户
     */
    private MpUser user;
    /**
     * 创建时间
     */
    private String createdAt;
    /**
     * 修改时间
     */
    private String updatedAt;
}
