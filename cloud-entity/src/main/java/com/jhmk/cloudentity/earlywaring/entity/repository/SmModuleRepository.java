package com.jhmk.cloudentity.earlywaring.entity.repository;

import com.jhmk.cloudentity.earlywaring.entity.SmModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SmModuleRepository extends JpaRepository<SmModule, String>, JpaSpecificationExecutor<SmModule> {
    List<SmModule> findByModCodeIn(List<String> moduleList);
}
