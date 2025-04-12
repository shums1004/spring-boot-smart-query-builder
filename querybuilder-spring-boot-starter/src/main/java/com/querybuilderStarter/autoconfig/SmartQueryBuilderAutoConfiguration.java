package com.querybuilderStarter.autoconfig;


import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.querybuilderStarter.SmartQueryBuilderFactory;
import com.smartQueryBuilder.SmartQueryBuilder;

@Configuration
@ConditionalOnClass(SmartQueryBuilder.class)
public class SmartQueryBuilderAutoConfiguration {

    @Bean
    public SmartQueryBuilderFactory smartQueryBuilderFactory(EntityManager entityManager) {
        return new SmartQueryBuilderFactory(entityManager);
    }
}