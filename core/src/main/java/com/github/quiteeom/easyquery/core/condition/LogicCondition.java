package com.github.quiteeom.easyquery.core.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LogicCondition is a kind of Condition
 *
 * group multi conditions with a logic condition with 'AND' or 'OR'
 *
 * @author quitee
 * @date 2022/11/30
 */

public class LogicCondition implements Condition{
    LogicType logicType;
    List<Condition> conditions = new ArrayList<>();

    public LogicCondition withConditions(Condition... conditions){
        this.conditions.addAll(Arrays.asList(conditions));
        return this;
    }

    public LogicCondition withConditions(List<Condition> conditions){
        this.conditions.addAll(conditions);
        return this;
    }

    public LogicCondition setLogicType(LogicType logicType){
        this.logicType = logicType;
        return this;
    }

    @Override
    public String toQueryString() {
        if (conditions.isEmpty()){
            return "";
        }else if (conditions.size()==1){
            return conditions.get(0).toQueryString();
        }else {
            StringBuilder queryStr = new StringBuilder("(").append(conditions.get(0).toQueryString());
            for (int i = 1; i< conditions.size(); i++){
                queryStr.append(" ")
                        .append(logicType.toQueryString())
                        .append(" ")
                        .append(conditions.get(i).toQueryString());
            }
            queryStr.append(")");
            return queryStr.toString();
        }
    }
}
