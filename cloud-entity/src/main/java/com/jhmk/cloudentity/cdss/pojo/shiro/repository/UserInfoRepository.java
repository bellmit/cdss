package com.jhmk.cloudentity.cdss.pojo.shiro.repository;

import com.jhmk.cloudentity.cdss.pojo.shiro.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ziyu.zhou
 * @date 2018/7/9 19:45
 */

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUsername(String username);
}
