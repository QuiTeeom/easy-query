package com.github.quiteeom.easyquery.core.values;

import static com.github.quiteeom.easyquery.core.values.Values.TYPE_NUMBER;

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

    public Number getNumber(){
        return raw;
    }

    @Override
    public String type() {
        return TYPE_NUMBER;
    }
}
