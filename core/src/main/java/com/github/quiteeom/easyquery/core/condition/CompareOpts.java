package com.github.quiteeom.easyquery.core.condition;

/**
 * @author quitee
 * @date 2022/11/30
 */

public enum CompareOpts implements CompareOpt{
    EQ("="),
    NOT_EQ("!="),

    GT(">"),
    GT_EQ(">="),
    LT("<"),
    LT_EQ("<="),

    IN("IN"),

    CONTAINS("contains"),
    START_WITH("startWith"),
    END_WITH("endWith"),
    MATCH("match"),

    BETWEEN("between"),
    ;

    final String queryStr;

    CompareOpts(String queryStr) {
        this.queryStr = queryStr;
    }

    @Override
    public String toQueryString() {
        return queryStr;
    }
}
