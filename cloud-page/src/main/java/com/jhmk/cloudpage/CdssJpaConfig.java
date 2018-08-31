package com.jhmk.cloudpage;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author zzy on 18/08/19.
 */
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager",
        basePackages = {"com.jhmk.cloudentity", "com.jhmk.cloudpage"},
        enableDefaultTransactions = false)
@Configuration
public class CdssJpaConfig {

}
