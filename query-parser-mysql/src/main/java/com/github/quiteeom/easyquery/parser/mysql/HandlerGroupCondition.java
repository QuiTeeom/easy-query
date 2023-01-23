package com.github.quiteeom.easyquery.parser.mysql;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.AstTraceFilters;
import com.github.quiteeom.easyquery.ast.AstTracer;
import com.github.quiteeom.easyquery.ast.TokenType;
import com.github.quiteeom.easyquery.ast.node.AstNodeGroupCondition;

import java.util.function.Function;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class HandlerGroupCondition implements AstMysqlHandler, Function<AstNode,Boolean> {
    @Override
    public void applyAstTracer(AstTracer astTracer) {
        astTracer.withCallBack(AstTraceFilters.GROUP_CONDITION,this);
    }

    @Override
    public Boolean apply(AstNode astNode) {
        AstNodeGroupCondition n = (AstNodeGroupCondition) astNode;

        // return if generated
        if (Helper.getSql(n)!=null){
            return false;
        }

        String left = Helper.getSql(n.getLeft());

        String right = Helper.getSql(n.getRight());

        String opt = n.getGroupType().getType()==TokenType.AND?"AND":"OR";

        String sql = (n.isParen()?"(":"")+left+" "+opt+" "+right+(n.isParen()?")":"");

        Helper.setSql(n,sql);
        return false;
    }
}
