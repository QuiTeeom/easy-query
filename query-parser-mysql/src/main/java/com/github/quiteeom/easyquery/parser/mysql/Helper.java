package com.github.quiteeom.easyquery.parser.mysql;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.core.values.Value;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class Helper {

    public static void setSql(AstNode astNode,String sql){
        astNode.getAttributes().put(Constants.MYSQL_PARSER,sql);
    }

    public static String getSql(AstNode astNode){
        return (String) astNode.getAttributes().get(Constants.MYSQL_PARSER);
    }

    public static void setSql(Value value, String sql){
        value.getAttributes().put(Constants.MYSQL_PARSER,sql);
    }

    public static String getSql(Value value){
        return (String) value.getAttributes().get(Constants.MYSQL_PARSER);
    }
}
