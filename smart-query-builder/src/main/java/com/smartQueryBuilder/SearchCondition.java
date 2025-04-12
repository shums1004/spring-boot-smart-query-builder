package com.smartQueryBuilder;

public class SearchCondition {
    public String field;
    public String searchText;
    public boolean fullText;

    public SearchCondition(String field, String searchText, boolean fullText) {
        this.field = field;
        this.searchText = searchText;
        this.fullText = fullText;
    }
}