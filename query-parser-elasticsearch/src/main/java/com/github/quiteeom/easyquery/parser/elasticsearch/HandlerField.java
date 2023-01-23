package com.github.quiteeom.easyquery.parser.elasticsearch;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.AstTraceFilters;
import com.github.quiteeom.easyquery.ast.AstTracer;
import com.github.quiteeom.easyquery.ast.node.AstNodeField;

import java.util.function.Function;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class HandlerField implements AstEsHandler, Function<AstNode,Boolean> {
    @Override
    public void applyAstTracer(AstTracer astTracer) {
        astTracer.withCallBack(AstTraceFilters.FIELD,this);
    }

    @Override
    public Boolean apply(AstNode astNode) {
        AstNodeField nodeField = (AstNodeField) astNode;
        String field = nodeField.toString();
        Helper.set(astNode,field);
        return false;
    }
}
