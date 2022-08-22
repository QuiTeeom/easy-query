package com.quitee.simplequery.builder.condition;

import com.quitee.simplequery.builder.utils.BuilderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quitee
 * @date 2022/8/21
 */

public class GroupConditionBuilder implements Condition {
    ConditionGroupType conditionGroupType;

    List<Condition> conditions = new ArrayList<>();

    public GroupConditionBuilder(ConditionGroupType conditionGroupType) {
        this.conditionGroupType = conditionGroupType;
    }

    public GroupConditionBuilder withCondition(Condition... conditions){
        for(Condition condition:conditions){
            this.conditions.add(condition);
        }
        return this;
    }

    public GroupConditionBuilder withType(ConditionGroupType conditionGroupType){
        this.conditionGroupType = conditionGroupType;
        return this;
    }

    public GroupConditionBuilder and(Condition... queries){
        return group(BasicConditionGroupType.AND,BasicConditionGroupType.AND,queries);
    }

    public GroupConditionBuilder and(ConditionGroupType conditionGroupType, Condition... queries){
        return group(BasicConditionGroupType.AND,conditionGroupType,queries);
    }

    public GroupConditionBuilder or(Condition... queries){
        return group(BasicConditionGroupType.OR,BasicConditionGroupType.OR,queries);
    }

    public GroupConditionBuilder or(ConditionGroupType conditionGroupType, Condition... queries){
        return group(BasicConditionGroupType.OR,conditionGroupType,queries);
    }

    public GroupConditionBuilder group(ConditionGroupType conditionGroupType, ConditionGroupType groupConditionGroupType, Condition... queries){
        GroupConditionBuilder res = new GroupConditionBuilder(conditionGroupType).withCondition(this);
        if (queries.length>0){
            res.withCondition(new GroupConditionBuilder(groupConditionGroupType).withCondition(queries));
        }
        return res;
    }

    public String buildQuery(){
        if (conditions.isEmpty()){
            return "";
        }else if (conditions.size()==1){
            return conditions.get(0).toString();
        }else {
            StringBuilder res = new StringBuilder("( ").append(conditions.get(0).toString());
            for (int i = 1; i< conditions.size(); i++){
                res.append(BuilderUtils.join("", conditionGroupType.getType(), conditions.get(i).toString()));
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
