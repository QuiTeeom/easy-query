package com.github.quiteeom.easyquery.querybuilder.conditionbuilder;

import com.github.quiteeom.easyquery.core.values.Value;

/**
 * @author quitee
 * @date 2022/11/30
 */

public interface CompareValueConvertor {
    Value convert(Object o);
}
