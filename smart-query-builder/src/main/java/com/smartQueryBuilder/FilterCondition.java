package com.smartQueryBuilder;

public class FilterCondition {
    public String field;
    public String operator;
    public Object value;

    public FilterCondition(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }
}