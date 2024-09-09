package com.web.platform.common;

import java.time.format.DateTimeFormatter;

/**
 * @author li
 **/
public class Const {

    /**
     * 创建token的秘钥
     */
    public static final String TOKEN_SECRET = "ZY78C6Sd3v";

    /**
     * 创建刷新token的秘钥
     */
    public static final String REFRESH_TOKEN_SECRET = "ZY78C6Sd3v";

    /**
     * 后台过期时间 1天
     */
    public static final long ADMIN_EXPIRE_TIME = 24L * 3600L * 1000L;

    public static final String CURRENT_LOGIN_USER = "CURRENT_LOGIN_USER";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

}
