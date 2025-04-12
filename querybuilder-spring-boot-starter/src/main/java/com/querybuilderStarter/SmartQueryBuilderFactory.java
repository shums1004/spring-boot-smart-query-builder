package com.querybuilderStarter;


import jakarta.persistence.EntityManager;
import com.smartQueryBuilder.SmartQueryBuilder;

public class SmartQueryBuilderFactory {

    private final EntityManager entityManager;

    public SmartQueryBuilderFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> SmartQueryBuilder<T> forEntity(Class<T> entityClass) {
        return new SmartQueryBuilder<>(entityClass).withEntityManager(entityManager);
    }
}