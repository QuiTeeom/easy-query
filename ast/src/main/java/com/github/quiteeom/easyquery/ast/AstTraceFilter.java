package com.github.quiteeom.easyquery.ast;

/**
 * @author quitee
 * @date 2022/10/22
 */

public interface AstTraceFilter {
    boolean filter(AstNode astNode,AstTraceContext ctx);
}
