package com.github.quiteeom.easyquery.parser.mysql;

import com.github.quiteeom.easyquery.ast.AstTracer;

/**
 * @author quitee
 * @date 2023/1/23
 */

public interface AstMysqlHandler {
    void applyAstTracer(AstTracer astTracer);
}
