package com.quitee.simplequery.builder.condition;

import com.quitee.simplequery.builder.utils.BuilderUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author quitee
 * @date 2022/8/21
 */

public class GroupConditionBuilder implements Condition {
    ConditionGroupType conditionGroupType;

    List<Condition> queries = new ArrayList<>();

    public GroupConditionBuilder(ConditionGroupType conditionGroupType) {
        this.conditionGroupType = conditionGroupType;
    }

    public GroupConditionBuilder withCondition(Condition... queries){
        this.queries.addAll(Arrays.asList(queries));
        return this;
    }

    public GroupConditionBuilder withType(ConditionGroupType conditionGroupType){
        this.conditionGroupType = conditionGroupType;
        return this;
    }

    public GroupConditionBuilder and(Condition... queries){
        return new GroupConditionBuilder(BasicConditionGroupType.AND).withCondition(this).withCondition(new GroupConditionBuilder(BasicConditionGroupType.AND).withCondition(queries));
    }

    public GroupConditionBuilder and(ConditionGroupType conditionGroupType, Condition... queries){
        return new GroupConditionBuilder(BasicConditionGroupType.AND).withCondition(this).withCondition(new GroupConditionBuilder(conditionGroupType).withCondition(queries));
    }

    public GroupConditionBuilder or(Condition... queries){
        return new GroupConditionBuilder(BasicConditionGroupType.OR).withCondition(this).withCondition(new GroupConditionBuilder(BasicConditionGroupType.AND).withCondition(queries));
    }

    public GroupConditionBuilder or(ConditionGroupType conditionGroupType, Condition... queries){
        return new GroupConditionBuilder(BasicConditionGroupType.AND).withCondition(this).withCondition(new GroupConditionBuilder(conditionGroupType).withCondition(queries));
    }

    public GroupConditionBuilder group(ConditionGroupType conditionGroupType, ConditionGroupType groupConditionGroupType, Condition... queries){
        return new GroupConditionBuilder(conditionGroupType).withCondition(this).withCondition(new GroupConditionBuilder(groupConditionGroupType).withCondition(queries));
    }

    public String buildQuery(){
        if (queries.isEmpty()){
            return "";
        }else if (queries.size()==1){
            return queries.get(0).toString();
        }else {
            StringBuilder res = new StringBuilder("( ").append(queries.get(0).toString());
            for (int i = 1; i< queries.size(); i++){
                res.append(BuilderUtils.join("", conditionGroupType.getType(), queries.get(i).toString()));
            }
            res.append(" )");
            return res.toString();
        }
    }

    @Override
    public String toString() {
        return buildQuery();
    }
}
