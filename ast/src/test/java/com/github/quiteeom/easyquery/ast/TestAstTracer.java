package com.github.quiteeom.easyquery.ast;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class TestAstTracer {
    public static void main(String[] args) {
        AstBuilder astBuilder = new AstBuilder();
        AstNode node = astBuilder.build("((((name = \"quitee\" AND age between (18,24]) OR (name contains \"guagua\" AND age <= 18)) OR createDate < 2022-12-18T14:48:19.886) OR createDate = TRUE)");
        AstTracer astTracer = AstTracer.getInstance();
        astTracer.withCallBack(AstTraceFilters.FIELD_CONDITION,astNode -> {
            System.out.println("比较条件:"+astNode.toString());
            return false;
        });

//        astTracer.withCallBack(AstTraceFilters.GROUP_CONDITION,astNode -> {
//            System.out.println("组合条件:"+astNode.toString());
//            return false;
//        });
        astTracer.LRD(node);
    }
}
