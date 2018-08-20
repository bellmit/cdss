package com.jhmk.cloudentity.earlywaring.entity.repository;

import com.jhmk.cloudentity.earlywaring.entity.SmRoleModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SmRoleModuleRepository extends JpaRepository<SmRoleModule, Integer>, JpaSpecificationExecutor<SmRoleModule> {
    List<SmRoleModule>findAllByRoleId(String roleId);
}
