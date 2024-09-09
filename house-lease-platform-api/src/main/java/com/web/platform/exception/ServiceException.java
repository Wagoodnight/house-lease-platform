package com.web.platform.exception;

import com.web.platform.common.ResponseType;


/**
 * @author zhenxun
 */
public class ServiceException extends RuntimeException {

    private final String code;

    public ServiceException(ResponseType responseType) {
        super(responseType.getMsg());
        this.code = responseType.getCode();
    }

    public String getCode() {
        return this.code;
    }
}
