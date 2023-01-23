package com.github.quiteeom.easyquery.core.values;

import static com.github.quiteeom.easyquery.core.values.Values.TYPE_STRING;

/**
 * just string
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

    public String getString(){
        return raw;
    }

    @Override
    public String type() {
        return TYPE_STRING;
    }
}
