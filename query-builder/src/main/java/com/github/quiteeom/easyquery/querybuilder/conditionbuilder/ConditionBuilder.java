package com.github.quiteeom.easyquery.querybuilder.conditionbuilder;

import com.github.quiteeom.easyquery.core.condition.Condition;
import com.github.quiteeom.easyquery.querybuilder.QueryBuilder;

/**
 * @author quitee
 * @date 2022/11/30
 */

public interface ConditionBuilder extends QueryBuilder {
    @Override
    Condition build();
}
