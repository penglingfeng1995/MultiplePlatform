package com.plf.mp.model;

import lombok.Data;

/**
 * @author plf
 */
public enum Status {
    /**
     * 请求成功
     */
    OK("ok"),
    /**
     * 请求失败
     */
    FAIL("fail");

    private String code;

    Status(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
