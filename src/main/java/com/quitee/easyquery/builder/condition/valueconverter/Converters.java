package com.quitee.easyquery.builder.condition.valueconverter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/8/22
 */

public class Converters {
    private static final Map<Class<?>,ValueConverter> converterMap = new HashMap<>();
    static {
        converterMap.put(String.class,new StringConverter());
        converterMap.put(Integer.class,new IntegerConverter());
        converterMap.put(int.class,new IntegerConverter());
        converterMap.put(Boolean.class,new StringConverter());
        converterMap.put(boolean.class,new IntegerConverter());
        converterMap.put(Long.class,new StringConverter());
        converterMap.put(long.class,new IntegerConverter());
        converterMap.put(Double.class,new StringConverter());
        converterMap.put(double.class,new IntegerConverter());
        converterMap.put(Date.class,new StringConverter());
    }

    public static <T> ValueConverter get(Class<T> from){
        return converterMap.getOrDefault(from,converterMap.get(String.class));
    }
}
