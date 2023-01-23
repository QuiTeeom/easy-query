package com.github.quiteeom.easyquery.core.condition;

import com.github.quiteeom.easyquery.core.values.Value;

/**
 * a compare condition contains
 * target
 * opt
 * value
 *
 * a compare condition is a kind of Condition
 *
 * compare target with value by opt
 *
 * @author quitee
 * @date 2022/11/30
 */

public interface CompareCondition extends Condition{
    String getTarget();

    CompareOpt getOpt();

    Value getValue();
}
