package com.jhmk.cloudentity.base;

import com.alibaba.fastjson.JSON;
import com.jhmk.cloudutil.config.BaseConstants;
import javafx.application.Application;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseController {

//	@Autowired
//    protected HttpServletRequest request;

    public void addSessionData(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public Object getSessionData(String key) {
        return getSession().getAttribute(key);
    }

    HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getHttpRequest().getSession();
        } catch (Exception e) {
        }
        return session;
    }

    HttpServletRequest getHttpRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attr.getRequest();
    }

    public String getUserId() {
        String userId = (String) getSessionData(BaseConstants.USER_ID);
        return userId;
    }

    public String getCurrentRole() {
        String currentRoleId = (String) getSessionData(BaseConstants.CURRENT_ROLE_ID);
        return currentRoleId;
    }

    /**
     * 获取权限范围 1 查看自己提交的规则 2.查看所有提交的规则
     *
     * @return
     */
    public String getCurrentRoleRange() {
        String range = (String) getSessionData(BaseConstants.CURRENT_ROLE_RANGE);
        return range;
    }


    public void setCurrentRoleId(String roleId) {
        addSessionData(BaseConstants.CURRENT_ROLE_ID, roleId);
    }


    public String getDeptId() {
        String currentOrgId = (String) getSessionData(BaseConstants.DEPT_ID);
        return currentOrgId;
    }

    public String getDeptName() {
        String deptName = (String) getSessionData(BaseConstants.DEPT_NAME);
        return deptName;
    }

//    public static void wirte(HttpServletResponse response, Object obj) {
//        response.setCharacterEncoding("utf-8");
//        try {
//            PrintWriter writer = response.getWriter();
//            writer.print(JSON.toJSON(obj));
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void wirte(HttpServletResponse response, Object obj) {
        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter writer = response.getWriter();
            String currentRoleRange = getCurrentRoleRange();
            if (StringUtils.isNotBlank(currentRoleRange)) {
                response.setHeader(BaseConstants.CURRENT_ROLE_RANGE, currentRoleRange);
            }
            writer.print(JSON.toJSON(obj));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}