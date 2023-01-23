package com.github.quiteeom.easyquery.core.values;

import java.util.Arrays;
import java.util.List;

import static com.github.quiteeom.easyquery.core.values.Values.TYPE_RANGE;

/**
 * (from,to)
 * [form,to]
 * [from,to)
 * (from,to]
 * @author quitee
 * @date 2022/11/30
 */

public class RangeValue extends BaseValue<List<?>> {
    Boundary fromBoundary;
    Value fromValue;

    Boundary toBoundary;
    Value toValue;

    final String queryStr;

    public RangeValue(Boundary fromBoundary, Value fromValue, Value toValue, Boundary toBoundary) {
        super(Arrays.asList(fromBoundary,fromBoundary,toValue,toBoundary));
        this.fromBoundary = fromBoundary;
        this.fromValue = fromValue;
        this.toBoundary = toBoundary;
        this.toValue = toValue;
        queryStr = (fromBoundary==Boundary.OPEN?"(":"[")
                + fromValue.toQueryString()
                + ","
                + toValue.toQueryString()
                + (toBoundary==Boundary.OPEN?")":"]");
    }

    @Override
    public String toQueryString() {
        return queryStr;
    }

    @Override
    public String type() {
        return TYPE_RANGE;
    }

    public Boundary getFromBoundary() {
        return fromBoundary;
    }

    public Value getFromValue() {
        return fromValue;
    }

    public Boundary getToBoundary() {
        return toBoundary;
    }

    public Value getToValue() {
        return toValue;
    }

    public static Boundary OPEN = Boundary.OPEN;
    public static Boundary CLOSE = Boundary.CLOSE;

    public enum Boundary{
        OPEN,
        CLOSE
        ;
    }
}
