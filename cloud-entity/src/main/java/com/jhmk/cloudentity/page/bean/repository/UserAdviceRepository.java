package com.jhmk.cloudentity.page.bean.repository;

import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudentity.page.bean.UserAdvice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserAdviceRepository extends PagingAndSortingRepository<UserAdvice, Integer>, JpaSpecificationExecutor<UserAdvice> {
}
