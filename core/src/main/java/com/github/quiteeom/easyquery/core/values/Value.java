package com.github.quiteeom.easyquery.core.values;

import com.github.quiteeom.easyquery.core.Query;

import java.util.Map;

/**
 * @author quitee
 * @date 2022/11/30
 */

public interface Value extends Query {
    String type();

    Map<String,Object> getAttributes();
}
