package com.jhmk.cloudpage.interceptor;

/**
 * @author ziyu.zhou
 * @date 2018/12/18 16:43
 */

import com.jhmk.cloudentity.common.LoggerBean;
import javassist.*;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * 配置页面log日志
 */
@Aspect
@Component
public class CloudPageAopLogInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CloudPageAopLogInterceptor.class);
    private static ThreadLocal<LoggerBean> param = new ThreadLocal<LoggerBean>();


//    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    @Pointcut("execution(public * com.jhmk.cloudpage.controller..*.*(..))")
    public void controllerMethodPointcut() {
    }


    //    @Pointcut("@annotation(org.springframework.beans.factory.annotation.)")

    //    @Pointcut("execution(public * org.springframework.web.client.RestTemplate.*(..))")
    @Pointcut("execution(public * org.springframework.web.client.RestTemplate.*(..))")
//    @Pointcut("execution(public * org.springframework.web.client.RestTemplate.postForObject())||execution(public * org.springframework.web.client.RestTemplate.getForObject())")
    public void restTempletePointcut() {
    }


    /**
     * 请求方法前打印内容 * * @param joinPoint
     */
    @Before("controllerMethodPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        // 请求开始时间
        LoggerBean loggerBean = new LoggerBean();
        loggerBean.setStartTime(System.currentTimeMillis());
//        startTime.set(System.currentTimeMillis());
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String uri = request.getRequestURI();
        loggerBean.setUri(uri);
        String method = request.getMethod();
        StringBuffer params = new StringBuffer();
        if (HttpMethod.GET.toString().equals(method)) {
            // get请求
            String queryString = request.getQueryString();
            if (StringUtils.isNotBlank(queryString)) {
//                params.append(URLEncodedUtils.encode(queryString, "UTF-8"));
                params.append(queryString);
            }
        } else {
            //其他请求
            Object[] paramsArray = joinPoint.getArgs();
            if (paramsArray != null && paramsArray.length > 0) {
                for (int i = 0; i < paramsArray.length; i++) {
                    if (paramsArray[i] instanceof Serializable) {
                        params.append(paramsArray[i].toString()).append(",");
                    }
                }
            }
        }
        loggerBean.setParams(params.toString());
//        key.set(uri);
        param.set(loggerBean);
        logger.info("request params>>>>>>{}", loggerBean.toString());
    }

    /**
     * 在方法执行后打印返回内容 * * @param obj
     */
    @AfterReturning(returning = "obj", pointcut = "controllerMethodPointcut()")
    public void doAfterReturing(Object obj) {
        LoggerBean loggerBean = param.get();
        long costTime = System.currentTimeMillis() - loggerBean.getStartTime();
        loggerBean.setRequestTime(costTime);
        String result = null;
        if (obj instanceof Serializable) {
            result = obj.toString();
        }
        loggerBean.setResult(result);
        logger.info("response result<<<<<<{}", loggerBean.toString());
        param.remove();
    }


    @Around("restTempletePointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws ChangeSetPersister.NotFoundException, NotFoundException {
        LoggerBean loggerBean = new LoggerBean();
        long startTime = System.currentTimeMillis();
        loggerBean.setStartTime(startTime);
//        String classType = pjp.getTarget().getClass().getName();
//        Class<?> clazz = null;
//        try {
//            clazz = Class.forName(classType);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        String clazzName = clazz.getName();
        //post
//        String methodName = pjp.getSignature().getName(); //获取调用方式名称
        Object[] args = pjp.getArgs();
        if (Objects.nonNull(args) && args.length > 2) {
            String url = args[0].toString();
            String param = args[1].toString();
            //请求参数
            loggerBean.setParams(param);
            //请求地址
            loggerBean.setUri(url);
        }
        // result的值就是被拦截方法的返回值
        Object result = null;
        try {
            result = pjp.proceed();
            if (Objects.nonNull(result)) {
                loggerBean.setResult(result.toString());
            }
            loggerBean.setRequestTime(System.currentTimeMillis() - startTime);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        logger.info("restTempLete调用结束： result<<<<<<{} ", loggerBean.toString());
        param.remove();
        return result;
    }

}
