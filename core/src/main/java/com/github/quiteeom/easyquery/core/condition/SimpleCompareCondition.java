package com.github.quiteeom.easyquery.core.condition;

import com.github.quiteeom.easyquery.core.values.Value;

/**
 * @author quitee
 * @date 2022/11/30
 */

public class SimpleCompareCondition implements CompareCondition{
    String target;
    CompareOpt opt;
    Value value;

    public SimpleCompareCondition(String target, CompareOpt opt, Value value) {
        if (target==null){
            throw new IllegalArgumentException("target could not be null");
        }

        if (opt==null){
            throw new IllegalArgumentException("opt could not be null");
        }

        if (value==null){
            throw new IllegalArgumentException("value could not be null");
        }

        this.target = target;
        this.opt = opt;
        this.value = value;
    }

    @Override
    public String toQueryString() {
        return target +
                " " +
                opt.toQueryString() +
                " " +
                value.toQueryString();
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public CompareOpt getOpt() {
        return opt;
    }

    @Override
    public Value getValue() {
        return value;
    }
}
