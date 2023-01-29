package com.github.quiteeom.easyquery.core.values;

import static com.github.quiteeom.easyquery.core.values.Values.TYPE_NULL;

/**
 * @author quitee
 * @date 2023/1/29
 */

public class NullValue extends BaseValue<Object>{
    private static final Object NULL = new Object();

    public static final NullValue INSTANCE = new NullValue();

    private NullValue() {
        super(NULL);
    }

    @Override
    public String toQueryString() {
        return "NULL";
    }

    @Override
    public String type() {
        return TYPE_NULL;
    }
}
