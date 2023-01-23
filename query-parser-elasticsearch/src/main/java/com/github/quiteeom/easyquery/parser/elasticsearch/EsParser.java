package com.github.quiteeom.easyquery.parser.elasticsearch;

import com.github.quiteeom.easyquery.ast.AstBuilder;
import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.AstTraceContext;
import com.github.quiteeom.easyquery.ast.AstTracer;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class EsParser {
    AstTracer astTracer = AstTracer.getInstance();
    List<AstEsHandler> handlers = new ArrayList<>();
    public EsParser(){
        addHandlers();
    }

    private void addHandlers(){
        addHandler(new HandlerFieldCondition());
        addHandler(new HandlerGroupCondition());
        addHandler(new HandlerField());
        addHandler(new HandlerValue());
    }

    private void addHandler(AstEsHandler astMysqlHandler){
        astMysqlHandler.applyAstTracer(astTracer);
    }


    public QueryBuilder parseAst(AstNode root){
        AstTraceContext context = new AstTraceContext();
        astTracer.LRD(root,context);
        return (QueryBuilder) Helper.get(root);
    }

    public QueryBuilder parseQuery(String query){
        AstBuilder astBuilder = new AstBuilder();
        AstNode astNode = astBuilder.build(query);
        return parseAst(astNode);
    }

}
