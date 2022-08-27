package com.quitee.simplequery.builder.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.quitee.simplequery.builder.condition.BasicConditionGroupType;
import com.quitee.simplequery.builder.condition.Condition;
import com.quitee.simplequery.builder.condition.Conditions;
import com.quitee.simplequery.builder.condition.FieldConditionBuilder;
import com.quitee.simplequery.parser.AstNode;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class BuildAndAst {
    public static void main(String[] args) {
        FieldConditionBuilder name = Conditions.filed("name");
        FieldConditionBuilder age = Conditions.filed("age");

        Condition condition = Conditions.group(
                name.eq("quitee"),
                age.eq(20)
        ).or(
                name.startWith("quitee"),
                age.lessEqThan(30)
        ).withType(BasicConditionGroupType.AND);

        System.out.println(condition.toString());

        com.quitee.simplequery.parser.AstBuilder astBuilder = new com.quitee.simplequery.parser.AstBuilder();
        AstNode astNode = astBuilder.build(condition.toString());

        System.out.println(astNode);
        System.out.println(JSON.toJSONString(astNode, JSONWriter.Feature.PrettyFormat));

    }
}
