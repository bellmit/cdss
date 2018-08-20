package com.jhmk.warn; /**
 * Copyright (c) 2017. 北京江融信科技有限公司 版权所有
 *
 * @author on 17/4/19.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author wangshuguan on 17/4/19.
 */
@EnableJpaRepositories(basePackages = {"com.jhmk.cloudservice.warnService", "com.jhmk.cloudentity.earlywaring.entity"}, enableDefaultTransactions = false)
@Configuration
public class JpaConfig {

}
