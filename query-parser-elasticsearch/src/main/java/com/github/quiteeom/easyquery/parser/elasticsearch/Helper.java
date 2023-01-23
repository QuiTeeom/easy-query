package com.github.quiteeom.easyquery.parser.elasticsearch;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.core.values.Value;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class Helper {

    public static void set(AstNode astNode,Object value){
        astNode.getAttributes().put(Constants.ES_PARSER,value);
    }

    public static Object get(AstNode astNode){
        return astNode.getAttributes().get(Constants.ES_PARSER);
    }

    public static void set(Value value, Object v){
        value.getAttributes().put(Constants.ES_PARSER,v);
    }

    public static Object get(Value value){
        return value.getAttributes().get(Constants.ES_PARSER);
    }
}
