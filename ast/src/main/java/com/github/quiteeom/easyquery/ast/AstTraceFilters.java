package com.github.quiteeom.easyquery.ast;

import com.github.quiteeom.easyquery.ast.node.AstNodeCompareFieldCondition;
import com.github.quiteeom.easyquery.ast.node.AstNodeField;
import com.github.quiteeom.easyquery.ast.node.AstNodeGroupCondition;
import com.github.quiteeom.easyquery.ast.node.AstNodeValue;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class AstTraceFilters {
    public static final AstTraceFilter FIELD = new AstTraceFilter() {
        @Override
        public boolean filter(AstNode astNode, AstTraceContext ctx) {
            return astNode instanceof AstNodeField;
        }
    };

    public static final AstTraceFilter VALUE = new AstTraceFilter() {
        @Override
        public boolean filter(AstNode astNode, AstTraceContext ctx) {
            return astNode instanceof AstNodeValue;
        }
    };

    public static final AstTraceFilter FIELD_CONDITION = new AstTraceFilter() {
        @Override
        public boolean filter(AstNode astNode, AstTraceContext ctx) {
            return astNode instanceof AstNodeCompareFieldCondition;
        }
    };

    public static final AstTraceFilter GROUP_CONDITION = new AstTraceFilter() {
        @Override
        public boolean filter(AstNode astNode, AstTraceContext ctx) {
            return astNode instanceof AstNodeGroupCondition;
        }
    };
}
