package com.quitee.easyquery.tracer;

import com.quitee.easyquery.ast.*;

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
    public static final AstTraceFilter FIELD_CONDITION_COMPARE = new AstTraceFilter() {
        @Override
        public boolean filter(AstNode astNode, AstTraceContext ctx) {
            return astNode instanceof AstNodeCompareFieldCondition;
        }
    };
    public static final AstTraceFilter FIELD_CONDITION_BETWEEN = new AstTraceFilter() {
        @Override
        public boolean filter(AstNode astNode, AstTraceContext ctx) {
            return astNode instanceof AstNodeBetweenFieldCondition;
        }
    };
    public static final AstTraceFilter FIELD_CONDITION_IN = new AstTraceFilter() {
        @Override
        public boolean filter(AstNode astNode, AstTraceContext ctx) {
            return astNode instanceof AstNodeInFieldCondition;
        }
    };
    public static final AstTraceFilter GROUP_CONDITION = new AstTraceFilter() {
        @Override
        public boolean filter(AstNode astNode, AstTraceContext ctx) {
            return astNode instanceof AstNodeGroupCondition;
        }
    };
}
