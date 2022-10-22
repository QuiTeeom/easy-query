package com.quitee.easyquery.tracer;

import com.quitee.easyquery.ast.AstNode;
import com.quitee.easyquery.ast.AstNodeBaseFieldCondition;
import com.quitee.easyquery.ast.AstNodeField;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class AstTraceFilters {
    public static final AstTraceFilter FIELD_CONDITION_FILTER = new AstTraceFilter() {
        @Override
        public boolean filter(AstNode astNode, AstTraceContext ctx) {
            return astNode instanceof AstNodeBaseFieldCondition;
        }
    };
    public static final AstTraceFilter FIELD_CONDITION_FIELD_FILTER = new AstTraceFilter() {
        @Override
        public boolean filter(AstNode astNode, AstTraceContext ctx) {
            return astNode instanceof AstNodeField;
        }
    };
}
