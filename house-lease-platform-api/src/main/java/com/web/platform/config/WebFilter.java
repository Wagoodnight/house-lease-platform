package com.web.platform.config;


import com.web.platform.common.Const;
import com.web.platform.common.Response;
import com.web.platform.common.ResponseType;
import com.web.platform.pojo.CurrentLoginUser;
import com.web.platform.util.ResponseUtil;
import com.web.platform.util.TokenUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author li
 */
@Configuration
public class WebFilter implements Filter {

    /**
     * 错误页面和登录注册放行
     */
    private final List<String> whiteList = Arrays.asList(
            "/doc.html",
            "/401",
            "/user/login",
            "/user/registry",
            "/user/update-pwd",
            "/404",
            "/",
            "/api/doc.html",
            "/api/401",
            "/api/user/login",
            "/api/user/registry",
            "/api/user/update-pwd",
            "/api/404",
            "/api/"
    );

    /**
     * 资源文件放行
     */
    private final List<String> suffixList = Arrays.asList(
            ".css",
            ".html",
            ".htm",
            ".jpg",
            ".png",
            ".jpeg",
            ".gif",
            ".ico"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");

        String uri = request.getRequestURI();
        if(uri.endsWith(".js")) {
            response.setContentType("text/javascript; utf-8");
            filterChain.doFilter(request, response);
            return;
        }
        if(suffixList.stream().anyMatch(uri::endsWith)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (whiteList.contains(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        if(uri.contains("/getVerify") || uri.contains("/webjars") || uri.contains("/api-docs") || uri.contains("/swagger")){
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization");
        if(ObjectUtils.isEmpty(token)) {
            ResponseUtil.responseJson(response, 200, Response.failed(ResponseType.NOT_LOGIN));
            return;
        } else if (!TokenUtil.verify(token)) {
            ResponseUtil.responseJson(response, 200, Response.failed(ResponseType.NOT_LOGIN));
            return;
        } else {
            CurrentLoginUser loginUser = TokenUtil.getPayload(token);
            request.setAttribute(Const.CURRENT_LOGIN_USER, loginUser);
        }
        filterChain.doFilter(request, response);
    }
}
