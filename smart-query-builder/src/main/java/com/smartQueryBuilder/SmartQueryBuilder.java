package com.smartQueryBuilder;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.util.*;

public class SmartQueryBuilder<T> {

    private final Class<T> entityClass;
    private final List<FilterCondition> filters = new ArrayList<>();
    private final List<SortCondition> sorts = new ArrayList<>();
    private final List<SearchCondition> searches = new ArrayList<>();
    private int page = 0;
    private int size = 10;
    private EntityManager entityManager;

    public SmartQueryBuilder(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public SmartQueryBuilder<T> withEntityManager(EntityManager em) {
        this.entityManager = em;
        return this;
    }

    public SmartQueryBuilder<T> filter(String field, String operator, Object value) {
        filters.add(new FilterCondition(field, operator, value));
        return this;
    }

    public SmartQueryBuilder<T> filterIfPresent(String field, String operator, Object value) {
        if (value != null && (!(value instanceof String) || !((String) value).isBlank())) {
            return filter(field, operator, value);
        }
        return this;
    }

    public SmartQueryBuilder<T> sort(String field, String direction) {
        sorts.add(new SortCondition(field, direction));
        return this;
    }

    public SmartQueryBuilder<T> search(String field, String text, boolean fullText) {
        searches.add(new SearchCondition(field, text, fullText));
        return this;
    }

    public SmartQueryBuilder<T> searchIfPresent(String field, String text, boolean fullText) {
        if (text != null && !text.isBlank()) {
            return search(field, text, fullText);
        }
        return this;
    }

    public SmartQueryBuilder<T> paginate(int page, int size) {
        this.page = Math.max(0,page);
        this.size = Math.min(100,size);
        return this;
    }

    public List<T> build() {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager not set. Please use withEntityManager() to provide it.");
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);

        List<Predicate> predicates = new ArrayList<>();

        for (FilterCondition fc : filters) {
            Path<?> path = root.get(fc.field);
            Object value = fc.value;

            switch (fc.operator) {
                case "=" -> predicates.add(cb.equal(path, value));
                case ">" -> {
                    if (value instanceof Comparable<?>) {
                        predicates.add(cb.greaterThan((Expression<Comparable>) path, (Comparable) value));
                    }
                }
                case "<" -> {
                    if (value instanceof Comparable<?>) {
                        predicates.add(cb.lessThan((Expression<Comparable>) path, (Comparable) value));
                    }
                }
                case ">=" -> {
                    if (value instanceof Comparable<?>) {
                        predicates.add(cb.greaterThanOrEqualTo((Expression<Comparable>) path, (Comparable) value));
                    }
                }
                case "<=" -> {
                    if (value instanceof Comparable<?>) {
                        predicates.add(cb.lessThanOrEqualTo((Expression<Comparable>) path, (Comparable) value));
                    }
                }
                case "like" -> predicates.add(cb.like(cb.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%"));
            }
        }

        for (SearchCondition sc : searches) {
            Path<String> path = root.get(sc.field);
            if (sc.fullText) {
                predicates.add(cb.like(cb.lower(path), "%" + sc.searchText.toLowerCase() + "%"));
            } else {
                predicates.add(cb.like(cb.lower(path), sc.searchText.toLowerCase() + "%"));
            }
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<Order> orders = new ArrayList<>();
        for (SortCondition sc : sorts) {
            orders.add("desc".equalsIgnoreCase(sc.direction) ? cb.desc(root.get(sc.field)) : cb.asc(root.get(sc.field)));
        }
        query.orderBy(orders);

        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }
}