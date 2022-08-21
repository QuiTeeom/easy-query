package com.quitee.simplequery.builder.condition;

/**
 *
 * @author quitee
 * @date 2022/8/21
 */
public class Conditions {

    public static FieldConditionBuilder filed(String field){
        return new FieldConditionBuilder(field);
    }

    public static FieldConditionBuilder filed(FieldNameProvider fieldNameProvider){
        return new FieldConditionBuilder(fieldNameProvider);
    }

    public static GroupConditionBuilder group(Condition... queries){
        return group(BasicConditionGroupType.AND, queries);
    }

    public static GroupConditionBuilder group(ConditionGroupType type, Condition... queries){
        return new GroupConditionBuilder(type).withCondition(queries);
    }
}
