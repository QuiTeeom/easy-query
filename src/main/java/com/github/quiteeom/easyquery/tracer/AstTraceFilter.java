package com.github.quiteeom.easyquery.tracer;

import com.github.quiteeom.easyquery.ast.AstNode;

/**
 * @author quitee
 * @date 2022/10/22
 */

public interface AstTraceFilter {
    boolean filter(AstNode astNode,AstTraceContext ctx);
}
