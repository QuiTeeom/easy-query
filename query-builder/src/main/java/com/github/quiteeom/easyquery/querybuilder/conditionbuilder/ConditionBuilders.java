package com.github.quiteeom.easyquery.querybuilder.conditionbuilder;

import com.github.quiteeom.easyquery.core.condition.LogicTypes;

/**
 * @author quitee
 * @date 2022/11/30
 */

public class ConditionBuilders {
    public static LogicConditionBuilder and(){
        return new LogicConditionBuilder(LogicTypes.AND);
    }

    public static LogicConditionBuilder or(){
        return new LogicConditionBuilder(LogicTypes.OR);
    }

    public static CompareConditionBuilder field(String target){
        return new CompareConditionBuilder(target);
    }

    public static CompareConditionBuilder field(CompareTargetProvider targetProvider){
        return new CompareConditionBuilder(targetProvider);
    }
}
