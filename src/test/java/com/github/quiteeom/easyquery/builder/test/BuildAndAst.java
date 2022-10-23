package com.github.quiteeom.easyquery.builder.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.TokenType;
import com.github.quiteeom.easyquery.builder.condition.*;
import com.github.quiteeom.easyquery.codec.mysql.MysqlParser;
import com.github.quiteeom.easyquery.parser.AstBuilder;
import com.github.quiteeom.easyquery.tracer.AstTracer;

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

        AstBuilder astBuilder = new AstBuilder();
        AstNode astNode = astBuilder.build(condition.toString());

        System.out.println(astNode);
        System.out.println(JSON.toJSONString(astNode, SerializerFeature.PrettyFormat));


        astNode = astBuilder.build("(name = 1 and b = 2 and d = s ) or ( as in ( 10,30 ))");
        System.out.println(astNode);

        AstTracer.getInstance().withCallBack(n-> {
            System.out.println(n);
            return false;
        }).LRD(astNode);
        System.out.println("trace ---------------- FILED_CONDITION_FILTER");

        astBuilder.getLexerConfig()
                .withAlias("等于", TokenType.EQ)
                .withAlias("处于", TokenType.IN)
                .withAlias("不等于", TokenType.NOT_EQ);
        astNode = astBuilder.build("(name 等于 1 and b 不等于 2 and d = s ) or ( as in ( 10,30 ))");

        MysqlParser parser = new MysqlParser();
        String s = parser.parser(astNode);
        System.out.println(s);
    }
}
