package com.github.quiteeom.easyquery.querybuilder.conditionbuilder;

import com.github.quiteeom.easyquery.core.condition.Condition;
import com.github.quiteeom.easyquery.core.condition.LogicCondition;
import com.github.quiteeom.easyquery.core.condition.LogicType;
import com.github.quiteeom.easyquery.core.condition.LogicTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quitee
 * @date 2022/11/30
 */

public class LogicConditionBuilder implements ConditionBuilder{

    LogicType logicType;
    List<ConditionBuilder> conditionBuilders = new ArrayList<>();

    LogicConditionBuilder(LogicType logicType) {
        this.logicType = logicType;
    }

    public LogicConditionBuilder withConditions(Object... objects){
        return withConditions(Arrays.asList(objects));
    }

    public LogicConditionBuilder withConditions(List<Object> objects){
        for (Object object : objects) {
            if (object instanceof String){
                conditionBuilders.add(new ConditionHolder(new QueryStringHolder((String) object)));
            }else if (object instanceof Condition){
                conditionBuilders.add(new ConditionHolder((Condition) object ));
            }else if (object instanceof ConditionBuilder){
                conditionBuilders.add((ConditionBuilder) object);
            }else {
                throw new IllegalArgumentException("unsupported condition:" + object);
            }
        }
        return this;
    }

    public LogicConditionBuilder and(ConditionBuilder... conditionBuilders){
        return with(LogicTypes.AND,new LogicConditionBuilder(LogicTypes.AND).withConditions(conditionBuilders));
    }

    public LogicConditionBuilder or(ConditionBuilder... conditionBuilders){
        return with(LogicTypes.OR,new LogicConditionBuilder(LogicTypes.AND).withConditions(conditionBuilders));
    }

    public LogicConditionBuilder with(LogicType withLogicType, LogicConditionBuilder builder){
        LogicConditionBuilder res = new LogicConditionBuilder(withLogicType);
        res.withConditions(this,builder);
        return res;
    }

    @Override
    public Condition build() {
        return new LogicCondition()
                .setLogicType(logicType)
                .withConditions(conditionBuilders.stream().map(ConditionBuilder::build).collect(Collectors.toList()));
    }

    private static class ConditionHolder implements ConditionBuilder{
        Condition condition;

        public ConditionHolder(Condition condition) {
            this.condition = condition;
        }

        @Override
        public Condition build() {
            return condition;
        }
    }

    private static class QueryStringHolder implements Condition{
        String queryStr;

        public QueryStringHolder(String queryStr) {
            this.queryStr = queryStr;
        }
        @Override
        public String toQueryString() {
            return queryStr;
        }
    }

}
