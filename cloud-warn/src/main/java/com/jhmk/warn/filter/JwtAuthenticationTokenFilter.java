package com.jhmk.warn.filter;


import com.alibaba.fastjson.JSON;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmUsersRepService;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);


    //    @Autowired
//    private AuthUserDetailsServiceImpl userDetailsService;
    @Autowired
    SmUsersRepService smUsersRepService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        String token = request.getHeader("token");
        Object sessionToken = request.getSession().getAttribute("token");
        //设置session过期时间，每次访问资源都会经过过滤器，如超过2小时时间不访问则过期
        response.setCharacterEncoding("utf-8");
        AtResponse resp = new AtResponse();

        if (token == null) {
            String requestURI = request.getRequestURI();
//            System.out.println(requestURI);
            if (requestURI.contains("ruleMatch")
                    || requestURI.contains("/match")
                    || requestURI.contains("warn/users")
                    || requestURI.contains("warn/dept")
                    || requestURI.contains("warn/login")
                    || requestURI.contains("/getShowLog")
                    || requestURI.contains("/updateShowLog")
                    || requestURI.contains("/getLogFile")
                    || requestURI.contains("/temp")
                    || requestURI.contains("/swagger-resources")
                    || requestURI.contains("/webjars")
                    || requestURI.contains("/v2")
                    || requestURI.contains("/swagger-ui.html")
                    || requestURI.contains("/favicon.ico")
                    ) {
//                response.reset();
                chain.doFilter(request, response);
            } else {
                logger.info("请求网址为：" + requestURI);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.setResponseCode(ResponseCode.INERERROR3);
                resp.setMessage("用户未登录，请重新登陆");

                getResponse(response, resp);
            }
        } else {
            if (sessionToken == null) {
                logger.info("用户登录过期，请重新登陆");
                resp.setResponseCode(ResponseCode.INERERROR5);

                getResponse(response, resp);
            } else {
                if (token.equals(sessionToken)) {
                    request.getSession().setMaxInactiveInterval(2 * 60 * 60);
//                    request.getSession().setMaxInactiveInterval(10);
                    String userId = (String) request.getSession().getAttribute(BaseConstants.USER_ID);

                    if (StringUtils.isEmpty(userId)) {
                        resp.setResponseCode(ResponseCode.INERERROR);
                        resp.setMessage("用户登录失效");

                        getResponse(response, resp);
                    } else {
                        chain.doFilter(request, response);
                    }
                } else {
                    logger.info("无效token" + token);
                    resp.setResponseCode(ResponseCode.INERERROR5);

                    getResponse(response, resp);
                }
            }
        }
    }

    private void getResponse(HttpServletResponse response, AtResponse resp) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(resp));
    }

}

