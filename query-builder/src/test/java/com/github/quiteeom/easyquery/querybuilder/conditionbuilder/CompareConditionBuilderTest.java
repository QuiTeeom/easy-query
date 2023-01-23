package com.github.quiteeom.easyquery.querybuilder.conditionbuilder;

import com.github.quiteeom.easyquery.core.values.BoundaryValue;

import java.time.LocalDateTime;

/**
 * @author quitee
 * @date 2022/11/30
 */

public class CompareConditionBuilderTest {
    public static void main(String[] args) {
        CompareConditionBuilder name = ConditionBuilders.field("name");
        CompareConditionBuilder age = ConditionBuilders.field("age");
        CompareConditionBuilder createDate = ConditionBuilders.field("createDate");

        System.out.println(
                name.eq("quitee")
                        .and(age.between(BoundaryValue.OPEN,18,24,BoundaryValue.CLOSE))
                        .or(name.contains("guagua").and(age.ltOrEq(18)))
                        .or(createDate.lt(LocalDateTime.now()))
                        .or(createDate.eq(true))
                        .build().toQueryString()
        );

        LogicConditionBuilder logicConditionBuilder = ConditionBuilders.and();
        logicConditionBuilder
                .withConditions("name = quitee")
                .withConditions(
                        name.in("a","b","c").build()
                );
        System.out.println(logicConditionBuilder.build().toQueryString());
    }
}
