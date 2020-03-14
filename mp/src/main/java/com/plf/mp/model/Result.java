package com.plf.mp.model;

import lombok.Data;

/**
 * @author plf
 */
@Data
public class Result<T> {

    /**
     * 请求状态 请使用 com.plf.mp.model.Status 的值
     */
    private String status;

    /**
     * 是否已登录
     */
    private Boolean isLogin;

    /**
     * 展示消息
     */
    private String msg;

    /**
     * 数据对象
     */
    private T data;
}
