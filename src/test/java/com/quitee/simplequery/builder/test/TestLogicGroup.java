package com.quitee.simplequery.builder.test;

import com.quitee.simplequery.builder.condition.BasicConditionGroupType;
import com.quitee.simplequery.builder.condition.FieldConditionBuilder;
import com.quitee.simplequery.builder.condition.Condition;
import com.quitee.simplequery.builder.condition.Conditions;

/**
 * @author quitee
 * @date 2022/8/21
 */

public class TestLogicGroup {


    public static void main(String[] args) {
        FieldConditionBuilder name = Conditions.filed("name");
        FieldConditionBuilder age = Conditions.filed("age");

        Condition condition = Conditions.group(
                name.eq("aaa"),
                age.eq(2)
        ).or(
                name.in("aaa","vvv"),
                age.eq(11)
        ).withType(BasicConditionGroupType.AND);
        System.out.println(Conditions.group(
                BasicConditionGroupType.OR,
                name.in("aaa","vvv"),
                age.eq(11)
        ));

        System.out.println(condition.toString());
    }
}
