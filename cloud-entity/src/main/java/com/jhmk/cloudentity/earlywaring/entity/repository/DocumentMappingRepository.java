package com.jhmk.cloudentity.earlywaring.entity.repository;

import com.jhmk.cloudentity.earlywaring.entity.DocumentMapping;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentMappingRepository extends PagingAndSortingRepository<DocumentMapping, Integer>, JpaSpecificationExecutor<DocumentMapping> {

    /**
     * 用英文名查询中文名
     * @param urlPath
     * @return
     */
    DocumentMapping findFirstByUrlPath(String urlPath);
}
