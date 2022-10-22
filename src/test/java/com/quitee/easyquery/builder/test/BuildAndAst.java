package com.quitee.easyquery.builder.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quitee.easyquery.ast.AstNode;
import com.quitee.easyquery.builder.condition.*;

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

        com.quitee.easyquery.parser.AstBuilder astBuilder = new com.quitee.easyquery.parser.AstBuilder();
        AstNode astNode = astBuilder.build(condition.toString());

        System.out.println(astNode);
        System.out.println(JSON.toJSONString(astNode, SerializerFeature.PrettyFormat));


        astNode = astBuilder.build("(name = 1 and b = 2 and d = s ) or ( as between ( 10,30 ] )");
        System.out.println(astNode);

    }
}
