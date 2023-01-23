package com.github.quiteeom.easyquery.parser.elasticsearch;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.AstTraceFilters;
import com.github.quiteeom.easyquery.ast.AstTracer;
import com.github.quiteeom.easyquery.ast.node.AstNodeCompareFieldCondition;
import com.github.quiteeom.easyquery.ast.node.AstNodeValue;
import com.github.quiteeom.easyquery.core.values.RangeValue;
import com.github.quiteeom.easyquery.core.values.Value;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

import java.util.function.Function;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class HandlerFieldCondition implements AstEsHandler, Function<AstNode,Boolean> {
    @Override
    public void applyAstTracer(AstTracer astTracer) {
        astTracer.withCallBack(AstTraceFilters.FIELD_CONDITION,this);
    }

    @Override
    public Boolean apply(AstNode astNode) {
        AstNodeCompareFieldCondition n = (AstNodeCompareFieldCondition) astNode;

        // return if generated
        if (Helper.get(n)!=null){
            return false;
        }

        String field = (String) Helper.get(n.getLeft());

        AstNodeValue<? extends Value> nodeValue = (AstNodeValue<? extends Value>) n.getRight();

        QueryBuilder queryBuilder = null;
        switch (n.getOperator().getType()){
            case EQ:{
                queryBuilder = QueryBuilders.matchQuery(field, Helper.get(nodeValue));
                break;
            }
            case NOT_EQ:{
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(field, Helper.get(nodeValue)));
                break;
            }
            case GT:{
                queryBuilder = QueryBuilders.rangeQuery(field).gt(Helper.get(nodeValue));
                break;
            }
            case GT_EQ:{
                queryBuilder = QueryBuilders.rangeQuery(field).gte(Helper.get(nodeValue));
                break;
            }
            case LT:{
                queryBuilder = QueryBuilders.rangeQuery(field).lt(Helper.get(nodeValue));
                break;
            }
            case LT_EQ:{
                queryBuilder = QueryBuilders.rangeQuery(field).lte(Helper.get(nodeValue));
                break;
            }
            case START_WITH:{
                queryBuilder = QueryBuilders.wildcardQuery(field,Helper.get(nodeValue).toString()+"*");
                break;
            }
            case CONTAINS:{
                queryBuilder = QueryBuilders.wildcardQuery(field,"*"+Helper.get(nodeValue).toString()+"*");
                break;
            }
            case END_WITH:{
                queryBuilder = QueryBuilders.wildcardQuery(field,"*"+Helper.get(nodeValue).toString());
                break;
            }
            case MATCH:{
                queryBuilder = QueryBuilders.regexpQuery(field,Helper.get(nodeValue).toString());
                break;
            }
            case BETWEEN:{
                queryBuilder = QueryBuilders.rangeQuery(field);

                RangeValue value = (RangeValue) nodeValue.getValue();
                Object from = Helper.get(value.getFromValue());
                Object to = Helper.get(value.getToValue());
                if (value.getFromBoundary()== RangeValue.Boundary.OPEN){
                    ((RangeQueryBuilder)queryBuilder).gt(from);
                }else {
                    ((RangeQueryBuilder)queryBuilder).gte(from);
                }

                if (value.getToBoundary()== RangeValue.Boundary.OPEN){
                    ((RangeQueryBuilder)queryBuilder).lt(to);
                }else {
                    ((RangeQueryBuilder)queryBuilder).lte(to);
                }
                break;
            }
            case IN:
            case NOT_IN:
            default:
                throw new RuntimeException("unsupported opt:"+n.getOperator().getType());
        }
        Helper.set(astNode,queryBuilder);
        return false;
    }
}
