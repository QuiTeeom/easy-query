package com.github.quiteeom.easyquery.core.condition;

/**
 * supported logic type
 *
 * @author quitee
 * @date 2022/11/30
 */

public enum LogicTypes implements LogicType{
    AND("AND"),
    OR("OR")
    ;

    final String queryStr;

    LogicTypes(String queryStr) {
        this.queryStr = queryStr;
    }

    @Override
    public String toQueryString() {
        return queryStr;
    }
}
