package com.quitee.easyquery.builder.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quitee.easyquery.ast.AstNode;
import com.quitee.easyquery.builder.condition.*;
import com.quitee.easyquery.tracer.AstTraceFilters;
import com.quitee.easyquery.tracer.AstTracer;

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


        astNode = astBuilder.build("(name = 1 and b = 2 and d = s ) or ( as in ( 10,30 ))");
        System.out.println(astNode);

        AstTracer.getInstance().withCallBack(n-> {
            System.out.println(n);
            return false;
        }).LRD(astNode);
        System.out.println("trace ---------------- FILED_CONDITION_FILTER");
        AstTracer.getInstance()
                .withCallBack(AstTraceFilters.FIELD_CONDITION_FILTER,n->{
                    System.out.println("完整条件："+n);
                    return false;
                })
                .withCallBack(AstTraceFilters.FIELD_CONDITION_FIELD_FILTER,n->{
                    System.out.println("字段名称："+n);
                    return false;
                })
                .LDR(astNode);


    }
}
