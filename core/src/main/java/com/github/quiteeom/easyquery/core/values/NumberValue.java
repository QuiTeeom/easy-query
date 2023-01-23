package com.github.quiteeom.easyquery.core.values;

/**
 * @author quitee
 * @date 2022/12/18
 */

public class NumberValue extends BaseValue<Number>{
    String queryStr;

    public NumberValue(Number raw) {
        super(raw);
        this.queryStr = raw.toString();
    }

    @Override
    public String toQueryString() {
        return queryStr;
    }

    @Override
    public String type() {
        return "number";
    }
}
