package com.github.quiteeom.easyquery.builder.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.quiteeom.easyquery.ast.AstNode;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstBuilder {
    public static void main(String[] args) {
        com.github.quiteeom.easyquery.parser.AstBuilder astBuilder = new com.github.quiteeom.easyquery.parser.AstBuilder();
        AstNode astNode = astBuilder.build("name startWith \"qui\" and (age between [10,20))");


        System.out.println(astNode);
        System.out.println(JSON.toJSONString(astNode, SerializerFeature.PrettyFormat));
    }
}
