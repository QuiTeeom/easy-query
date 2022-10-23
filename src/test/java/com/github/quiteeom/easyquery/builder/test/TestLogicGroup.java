package com.github.quiteeom.easyquery.builder.test;

import com.github.quiteeom.easyquery.builder.condition.BasicConditionGroupType;
import com.github.quiteeom.easyquery.builder.condition.FieldConditionBuilder;
import com.github.quiteeom.easyquery.builder.condition.Condition;
import com.github.quiteeom.easyquery.builder.condition.Conditions;

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
