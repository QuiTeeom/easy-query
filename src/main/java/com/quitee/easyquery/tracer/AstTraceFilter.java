package com.quitee.easyquery.tracer;

import com.quitee.easyquery.ast.AstNode;

/**
 * @author quitee
 * @date 2022/10/22
 */

public interface AstTraceFilter {
    boolean filter(AstNode astNode,AstTraceContext ctx);
}
