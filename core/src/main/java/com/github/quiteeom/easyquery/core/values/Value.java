package com.github.quiteeom.easyquery.core.values;

import com.github.quiteeom.easyquery.core.Query;

import java.util.Map;

/**
 * Value is a kind of Query
 * @author quitee
 * @date 2022/11/30
 */

public interface Value extends Query {
    /**
     * a value must have a type
     * @return type of the value
     */
    String type();

    /**
     * additional info if needed
     * @return attrs
     */
    Map<String,Object> getAttributes();
}
