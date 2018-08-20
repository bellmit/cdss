package com.jhmk.cloudentity.cdss.pojo.repository;

import com.jhmk.cloudentity.cdss.pojo.ShiroUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 16:27
 */

public interface ShiroUserRepository extends JpaRepository<ShiroUser, String>,JpaSpecificationExecutor<ShiroUser> {
}
