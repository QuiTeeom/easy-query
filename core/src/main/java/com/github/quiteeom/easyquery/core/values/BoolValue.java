package com.github.quiteeom.easyquery.core.values;

import static com.github.quiteeom.easyquery.core.values.Values.TYPE_BOOL;

/**
 * boolean value
 *
 * @author quitee
 * @date 2022/12/18
 */

public class BoolValue extends BaseValue<Boolean>{
    private final String queryStr;

    private BoolValue(Boolean raw) {
        super(raw);
        this.queryStr = raw?"TRUE":"FALSE";
    }

    public boolean isTrue() {
        return raw;
    }

    public boolean isFalse() {
        return !raw;
    }

    @Override
    public String toQueryString() {
        return queryStr;
    }

    @Override
    public String type() {
        return TYPE_BOOL;
    }

    public static final BoolValue TRUE = new BoolValue(true);
    public static final BoolValue FALSE = new BoolValue(false);
}
