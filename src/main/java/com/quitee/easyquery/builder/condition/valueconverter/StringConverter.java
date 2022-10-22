package com.quitee.easyquery.builder.condition.valueconverter;

/**
 * @author quitee
 * @date 2022/8/22
 */

public class StringConverter implements ValueConverter{
    @Override
    public String convert(Object s) {
        return "\""+s+"\"";
    }
}
