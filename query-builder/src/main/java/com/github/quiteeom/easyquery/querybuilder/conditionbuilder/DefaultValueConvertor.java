package com.github.quiteeom.easyquery.querybuilder.conditionbuilder;


import com.github.quiteeom.easyquery.core.values.ArrayValue;
import com.github.quiteeom.easyquery.core.values.BoolValue;
import com.github.quiteeom.easyquery.core.values.LocalDateTimeValue;
import com.github.quiteeom.easyquery.core.values.NumberValue;
import com.github.quiteeom.easyquery.core.values.StringValue;
import com.github.quiteeom.easyquery.core.values.Value;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quitee
 * @date 2022/11/30
 */

public class DefaultValueConvertor implements CompareValueConvertor{

    public DefaultValueConvertor(){
    }

    @Override
    public Value convert(Object o) {
        if (o instanceof String){
            return new StringValue((String) o);
        }else if (o instanceof Number){
            return new NumberValue((Number) o);
        }else if (o instanceof Collection){
            Collection<?> c = (Collection<?>) o;
            List<Value> values = c.stream().map(this::convert).collect(Collectors.toList());
            return new ArrayValue(values);
        }else if (o instanceof Date){
            return new LocalDateTimeValue((Date)o);
        }else if (o instanceof Boolean){
            return new BoolValue((Boolean)o);
        }

        return o::toString;
    }
}
