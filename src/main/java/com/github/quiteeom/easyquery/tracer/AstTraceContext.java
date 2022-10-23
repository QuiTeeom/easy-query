package com.github.quiteeom.easyquery.tracer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class AstTraceContext {
    boolean stop =false;

    Map<String,Object> attributes = new HashMap<>();

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
