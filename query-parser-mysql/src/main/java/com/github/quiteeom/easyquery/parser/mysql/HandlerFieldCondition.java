package com.github.quiteeom.easyquery.parser.mysql;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.AstTraceFilters;
import com.github.quiteeom.easyquery.ast.AstTracer;
import com.github.quiteeom.easyquery.ast.node.AstNodeCompareFieldCondition;
import com.github.quiteeom.easyquery.ast.node.AstNodeValue;
import com.github.quiteeom.easyquery.core.values.ArrayValue;
import com.github.quiteeom.easyquery.core.values.RangeValue;
import com.github.quiteeom.easyquery.core.values.Value;
import com.github.quiteeom.easyquery.core.values.Values;

import java.util.function.Function;

import static com.github.quiteeom.easyquery.parser.mysql.Constants.MYSQL_VALUE_BETWEEN_FROM;
import static com.github.quiteeom.easyquery.parser.mysql.Constants.MYSQL_VALUE_BETWEEN_TO;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class HandlerFieldCondition implements AstMysqlHandler, Function<AstNode,Boolean> {
    @Override
    public void applyAstTracer(AstTracer astTracer) {
        astTracer.withCallBack(AstTraceFilters.FIELD_CONDITION,this);
    }

    @Override
    public Boolean apply(AstNode astNode) {
        AstNodeCompareFieldCondition n = (AstNodeCompareFieldCondition) astNode;

        // return if generated
        if (Helper.getSql(n)!=null){
            return false;
        }

        String field = Helper.getSql(n.getLeft());
        // generate sql as default
        if (field==null){
            field = "`"+n.getLeft().toString()+"`";
        }

        AstNodeValue<? extends Value> nodeValue = (AstNodeValue<? extends Value>) n.getRight();


        StringBuilder sql = new StringBuilder().append(field);

        switch (n.getOperator().getType()){
            case EQ:
                dealDefaultOpt(sql,"=",nodeValue);
                break;
            case NOT_EQ:
                dealDefaultOpt(sql,"!=",nodeValue);
                break;
            case GT:
                dealDefaultOpt(sql,">",nodeValue);
                break;
            case GT_EQ:
                dealDefaultOpt(sql,">=",nodeValue);
                break;
            case LT:
                dealDefaultOpt(sql,"<",nodeValue);
                break;
            case LT_EQ:
                dealDefaultOpt(sql,"<=",nodeValue);
                break;
            case START_WITH:
                dealLike(sql,nodeValue,2);
                break;
            case CONTAINS:
                dealLike(sql,nodeValue,3);
                break;
            case END_WITH:
                dealLike(sql,nodeValue,1);
                break;
            case MATCH:
                dealRegexp(sql,nodeValue);
                break;
            case BETWEEN:
                sql = new StringBuilder();
                dealBetween(field,sql,nodeValue);
                break;
            case IN:
                dealIn(sql,nodeValue,false);
                break;
            case NOT_IN:
                dealIn(sql,nodeValue,true);
                break;
            default:
                throw new RuntimeException("unsupported opt:"+n.getOperator().getType());
        }

        Helper.setSql(n,sql.toString());
        return false;
    }

    private void dealRegexp(StringBuilder stringBuilder,AstNodeValue<? extends Value> astNodeValue){
        String valueSql = Helper.getSql(astNodeValue);
        stringBuilder.append("REGEXP")
                .append(" '")
                .append(valueSql)
                .append("' ");
    }

    /**
     * deal like
     * @param stringBuilder sb
     * @param astNodeValue value
     * @param type 1: %xx
     *             2: xx%
     *             3: %xx%
     */
    private void dealLike(StringBuilder stringBuilder,AstNodeValue<? extends Value> astNodeValue,int type){
        Value value = astNodeValue.getValue();
        String queryStr = value.toQueryString();
        queryStr = queryStr.substring(1,queryStr.length()-1);
        stringBuilder.append(" ").append("LIKE")
                .append(" ");
        switch (type){
            case 1: stringBuilder.append("'").append(queryStr).append("%").append("'");break;
            case 2: stringBuilder.append("'").append("%").append(queryStr).append("'");break;
            case 3: stringBuilder.append("'").append("%").append(queryStr).append("%").append("'");break;
        }
    }
    private void dealDefaultOpt(StringBuilder stringBuilder,String opt, AstNodeValue<? extends Value> astNodeValue){
        Value value = astNodeValue.getValue();
        stringBuilder.append(" ").append(opt)
                .append(" ");
        switch (value.type()){
            case Values.TYPE_DATE_TIME:
            case Values.TYPE_STRING:
            case Values.TYPE_NUMBER:
            case Values.TYPE_BOOL:
                stringBuilder.append(Helper.getSql(value));
                break;
            default:
                throw new IllegalArgumentException("比较操作不允许"+value.type()+"的值类型");
        }
    }
    private void dealBetween(String field,StringBuilder sql,AstNodeValue<? extends Value> astNodeValue){
        RangeValue value = (RangeValue) astNodeValue.getValue();

        String from = field +
                (value.getFromBoundary() == RangeValue.OPEN ? " > " : " >= ") +
                value.getAttributes().get(MYSQL_VALUE_BETWEEN_FROM);

        String to = field +
                (value.getToBoundary() == RangeValue.OPEN ? " < " : " <= ") +
                value.getAttributes().get(MYSQL_VALUE_BETWEEN_TO);

        sql.append("((").append(from).append(") AND (").append(to).append("))");
    }

    private void dealIn(StringBuilder sql, AstNodeValue<? extends Value> astNodeValue,boolean notIn){
        ArrayValue value = (ArrayValue) astNodeValue.getValue();

        sql.append(" ").append(notIn?"NOT IN":"IN").append(" ").append(Helper.getSql(value));
    }
}
