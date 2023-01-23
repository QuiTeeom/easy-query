package com.github.quiteeom.easyquery.core.values;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class
 * holds a raw value
 *
 * @author quitee
 * @date 2022/12/18
 */

public abstract class BaseValue<T> implements Value {
    protected T raw;

    public BaseValue(T raw) {
        this.raw = raw;
    }

    public T getRaw() {
        return raw;
    }

    Map<String,Object> attrs = new HashMap<>();

    @Override
    public Map<String, Object> getAttributes() {
        return attrs;
    }
}
