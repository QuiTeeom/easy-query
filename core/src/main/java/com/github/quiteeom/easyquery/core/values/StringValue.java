package com.github.quiteeom.easyquery.core.values;

/**
 * @author quitee
 * @date 2022/12/18
 */

public class StringValue extends BaseValue<String>{
    public StringValue(String raw) {
        super(raw);
    }

    @Override
    public String toQueryString() {
        return "\""+raw+"\"";
    }

    @Override
    public String type() {
        return "string";
    }
}
