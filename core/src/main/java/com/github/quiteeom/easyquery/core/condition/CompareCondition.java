package com.github.quiteeom.easyquery.core.condition;

import com.github.quiteeom.easyquery.core.values.Value;

/**
 * @author quitee
 * @date 2022/11/30
 */

public interface CompareCondition extends Condition{
    String getTarget();

    CompareOpt getOpt();

    Value getValue();
}
