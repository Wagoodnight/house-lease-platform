package com.web.platform.aspect;

import com.alibaba.fastjson.JSON;
import com.web.platform.common.Const;
import com.web.platform.common.Response;
import com.web.platform.entity.SysLog;
import com.web.platform.mapper.SysLogMapper;
import com.web.platform.pojo.CurrentLoginUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author ChatGPT
 */
@Aspect
@Component
public class ControllerLoggingAspect {

    @SuppressWarnings("all")
    @Autowired
    private SysLogMapper sysLogMapper;

    private static final Logger log = LoggerFactory.getLogger(ControllerLoggingAspect.class);

    @Pointcut("execution(* com.web.platform.controller..*(..))")
    public void controllerMethods() {
    }

    @Around("controllerMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前的 HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        // 初始化返回值
        Object result;

        try {
            // 获取当前登录用户
            CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);

            // 如果没有拿到登录用户，则不进行日志记录，直接返回执行结果
            if (loginUser == null) {
                return joinPoint.proceed();
            }

            // 记录方法名称
            String methodName = joinPoint.getSignature().getName();
            // 记录方法参数
            Object[] args = joinPoint.getArgs();
            // 创建日志对象
            SysLog sysLog = new SysLog();
            sysLog.setOperateName(methodName);
            sysLog.setParameter(JSON.toJSONString(args));
            sysLog.setUserId(loginUser.getUserId());

            // 执行目标方法并获取返回值
            result = joinPoint.proceed();

            // 如果返回值是 Response 类型，记录返回码
            if (result instanceof Response) {
                String code = ((Response<?>) result).getCode();
                sysLog.setResult(code);
            }

            //插入数据库
            if (sysLogMapper.insert(sysLog) < 1) {
                log.error("Insert log Error!, log: " + sysLog);
            }

        } catch (Exception e) {
            // 记录异常日志，保证controller层逻辑不受影响
            log.error("Logging aspect encountered an error: ", e);

            // 确保目标方法继续执行
            result = joinPoint.proceed();
        }

        return result;
    }
}

