package com.smartQueryBuilder;

public class SortCondition {
    public String field;
    public String direction;

    public SortCondition(String field, String direction) {
        this.field = field;
        this.direction = direction;
    }
}