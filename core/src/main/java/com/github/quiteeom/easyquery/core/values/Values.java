package com.github.quiteeom.easyquery.core.values;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class Values {

    public static final String TYPE_ARRAY = "array";
    public static ArrayValue array(Collection<Value> values){
        return new ArrayValue(values);
    }

    public static final String TYPE_BOOL = "bool";
    public static BoolValue boolTrue(){
        return BoolValue.TRUE;
    }
    public static BoolValue boolFalse(){
        return BoolValue.FALSE;
    }

    public static final String TYPE_DATE_TIME = "datetime";
    public static LocalDateTimeValue of(Date date){
        return new LocalDateTimeValue(date);
    }
    public static LocalDateTimeValue of(LocalDateTime localDateTime){
        return new LocalDateTimeValue(localDateTime);
    }

    public static final String TYPE_NUMBER = "number";
    public static NumberValue of(Number number){
        return new NumberValue(number);
    }

    public static final String TYPE_RANGE = "range";
    public static RangeValue range(Number from,Number to){
        return new RangeValue(RangeValue.Boundary.CLOSE,of(from),of(to), RangeValue.Boundary.CLOSE);
    }
    public static RangeValue range(Date from,Date to){
        return new RangeValue(RangeValue.Boundary.CLOSE,of(from),of(to), RangeValue.Boundary.CLOSE);
    }

    public static final String TYPE_STRING = "string";
    public static StringValue of(String string){
        return new StringValue(string);
    }


    public static final String TYPE_NULL = "null";
    public static NullValue ofNull(){
        return NullValue.INSTANCE;
    }

}
