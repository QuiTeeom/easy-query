package com.github.quiteeom.easyquery.parser.elasticsearch;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.AstTraceFilters;
import com.github.quiteeom.easyquery.ast.AstTracer;
import com.github.quiteeom.easyquery.ast.TokenType;
import com.github.quiteeom.easyquery.ast.node.AstNodeGroupCondition;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;
import java.util.function.Function;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class HandlerGroupCondition implements AstEsHandler, Function<AstNode,Boolean> {
    @Override
    public void applyAstTracer(AstTracer astTracer) {
        astTracer.withCallBack(AstTraceFilters.GROUP_CONDITION,this);
    }

    @Override
    public Boolean apply(AstNode astNode) {
        AstNodeGroupCondition n = (AstNodeGroupCondition) astNode;

        // return if generated
        if (Helper.get(n)!=null){
            return false;
        }

        QueryBuilder left = (QueryBuilder) Helper.get(n.getLeft());

        QueryBuilder right = (QueryBuilder) Helper.get(n.getRight());

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        if (n.getGroupType().getType()==TokenType.AND){
            List<QueryBuilder> must = queryBuilder.must();
            must.add(left);
            must.add(right);
        }else {
            List<QueryBuilder> must = queryBuilder.should();
            must.add(left);
            must.add(right);
        }

        Helper.set(n,queryBuilder);
        return false;
    }
}
