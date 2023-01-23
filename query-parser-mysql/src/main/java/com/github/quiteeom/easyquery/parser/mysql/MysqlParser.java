package com.github.quiteeom.easyquery.parser.mysql;

import com.github.quiteeom.easyquery.ast.AstBuilder;
import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.AstTraceContext;
import com.github.quiteeom.easyquery.ast.AstTracer;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class MysqlParser {
    AstTracer astTracer = AstTracer.getInstance();
    AstBuilder astBuilder = new AstBuilder();
    public MysqlParser(){
        addHandlers();
    }

    private void addHandlers(){
        addHandler(new HandlerFieldCondition());
        addHandler(new HandlerGroupCondition());
        addHandler(new HandlerField());
        addHandler(new HandlerValue());
    }

    private void addHandler(AstMysqlHandler astMysqlHandler){
        astMysqlHandler.applyAstTracer(astTracer);
    }


    public String parseAst(AstNode root){
        AstTraceContext context = new AstTraceContext();
        astTracer.LRD(root,context);
        return Helper.getSql(root);
    }

    public String parseQuery(String query){
        AstNode astNode = astBuilder.build(query);
        return parseAst(astNode);
    }

}
