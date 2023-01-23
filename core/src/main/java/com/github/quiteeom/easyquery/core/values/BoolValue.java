package com.github.quiteeom.easyquery.core.values;

/**
 * @author quitee
 * @date 2022/12/18
 */

public class BoolValue extends BaseValue<Boolean>{
    private String queryStr;

    public BoolValue(Boolean raw) {
        super(raw);
        this.queryStr = raw?"TRUE":"FALSE";
    }

    public BoolValue(BoolValue value) {
        this(value.raw);
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
        return "bool";
    }

    public static final BoolValue TRUE = new BoolValue(true);
    public static final BoolValue FALSE = new BoolValue(false);
}
