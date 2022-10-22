package com.quitee.simplequery.builder.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quitee.simplequery.Edge;
import com.quitee.simplequery.builder.condition.BasicConditionGroupType;
import com.quitee.simplequery.builder.condition.Condition;
import com.quitee.simplequery.builder.condition.Conditions;
import com.quitee.simplequery.builder.condition.FieldConditionBuilder;
import com.quitee.simplequery.ast.AstNode;

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
                age.between(10, Edge.include,30,Edge.exclude)
        ).withType(BasicConditionGroupType.OR);

        System.out.println(condition.toString());

        com.quitee.simplequery.parser.AstBuilder astBuilder = new com.quitee.simplequery.parser.AstBuilder();
        AstNode astNode = astBuilder.build(condition.toString());

        System.out.println(astNode);
        System.out.println(JSON.toJSONString(astNode, SerializerFeature.PrettyFormat));

    }
}
