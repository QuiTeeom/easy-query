package com.github.quiteeom.easyquery.builder.condition.valueconverter;

/**
 * @author quitee
 * @date 2022/8/22
 */

public class IntegerConverter implements ValueConverter{
    @Override
    public String convert(Object integer) {
        return integer.toString();
    }
}
