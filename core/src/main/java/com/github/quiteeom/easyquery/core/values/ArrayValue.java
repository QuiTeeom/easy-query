package com.github.quiteeom.easyquery.core.values;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.github.quiteeom.easyquery.core.values.Values.TYPE_ARRAY;

/**
 * collection of values
 * @author quitee
 * @date 2022/12/18
 */

public class ArrayValue extends BaseValue<Collection<Value>> {
    public ArrayValue(Collection<Value> values) {
        super(values);
        if (raw==null){
            raw = new ArrayList<>();
        }
    }

    public Collection<Value> getValues(){
        return raw;
    }

    @Override
    public String toQueryString() {
        return "("+ raw.stream().map(Value::toQueryString).collect(Collectors.joining(","))+")";
    }

    @Override
    public String type() {
        return TYPE_ARRAY;
    }
}
