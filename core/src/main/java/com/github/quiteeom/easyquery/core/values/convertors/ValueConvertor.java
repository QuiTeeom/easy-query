package com.github.quiteeom.easyquery.core.values.convertors;

/**
 * @author quitee
 * @date 2022/12/18
 */

public interface ValueConvertor<Value> {
    Value convertString(String raw);
}
