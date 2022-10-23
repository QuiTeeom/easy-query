package com.github.quiteeom.easyquery.codec.mysql;

import com.github.quiteeom.easyquery.ast.*;
import com.github.quiteeom.easyquery.tracer.AstTraceContext;
import com.github.quiteeom.easyquery.tracer.AstTraceFilters;
import com.github.quiteeom.easyquery.tracer.AstTracer;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class MysqlParser {

    public String parser(AstNode root){

        AstTraceContext context = new AstTraceContext();

        AstTracer.getInstance()
                .withCallBack(AstTraceFilters.FIELD_CONDITION_COMPARE,n->{
                    AstNodeCompareFieldCondition compareFieldCondition = (AstNodeCompareFieldCondition) n;

                    String field = "`"+n.getLeft().toString()+"`";
                    String value = n.getRight().toString();
                    String sql = "";
                    switch (compareFieldCondition.getOperator().getType()){
                        case EQ:
                            sql = field+" = '"+value+"'";
                            break;
                        case NOT_EQ:
                            sql = field+" != '"+value+"'";
                            break;
                        case GT:
                            sql = field+" > '"+value+"'";
                            break;
                        case GT_EQ:
                            sql = field+" >= '"+value+"'";
                            break;
                        case LT:
                            sql = field+" < '"+value+"'";
                            break;
                        case LT_EQ:
                            sql = field+" <= '"+value+"'";
                            break;
                        case START_WITH:
                            sql = field+" LIKE '"+value+"%'";
                            break;
                        case MATCH:
                            sql = field+" regexp '"+value+"'";
                            break;
                        case CONTAINS:
                            sql = field+" LIKE '%"+value+"%'";
                            break;
                        case END_WITH:
                            sql = field+" LIKE '%"+value+"'";
                            break;
                    }
                    n.getAttributes().put("MYSQL_SQL",sql);
                    return false;
                })
                .withCallBack(AstTraceFilters.FIELD_CONDITION_BETWEEN,n->{
                    AstNodeBetweenFieldCondition betweenFieldCondition = (AstNodeBetweenFieldCondition) n;

                    String field = "`"+n.getLeft().toString()+"`";
                    String sql = "";

                    if (betweenFieldCondition.isFromInclude()){
                        sql = sql + field+" >= "+betweenFieldCondition.getFrom();
                    }else {
                        sql = sql + field+" > "+betweenFieldCondition.getFrom();
                    }
                    sql = sql + " AND ";
                    if (betweenFieldCondition.isToInclude()){
                        sql = sql + field+" <= "+betweenFieldCondition.getFrom();
                    }else {
                        sql = sql +  field+" < "+betweenFieldCondition.getFrom();
                    }

                    n.getAttributes().put("MYSQL_SQL","("+sql+")");
                    return false;
                })
                .withCallBack(AstTraceFilters.FIELD_CONDITION_IN,n->{
                    AstNodeInFieldCondition betweenFieldCondition = (AstNodeInFieldCondition) n;

                    String field = "`"+n.getLeft().toString()+"`";
                    String sql = field + " IN (";

                    for (Token value : betweenFieldCondition.getValues()) {
                        sql = sql + value.getLiteral().getValue()+",";
                    }
                    sql = sql.substring(0,sql.length()-1)+")";
                    n.getAttributes().put("MYSQL_SQL",sql);
                    return false;
                })
                .withCallBack(AstTraceFilters.GROUP_CONDITION,n->{
                    AstNodeGroupCondition groupCondition = (AstNodeGroupCondition) n;

                    String sqlLeft = (String) groupCondition.getLeft().getAttributes().get("MYSQL_SQL");
                    String sqlRight = (String) groupCondition.getRight().getAttributes().get("MYSQL_SQL");
                    TokenType type = groupCondition.getGroupType().getType();

                    String sql = "";

                    if (type==TokenType.AND){
                        sql = sqlLeft + " AND " +sqlRight;
                    }else {
                        sql = sqlLeft + " OR " +sqlRight;
                    }
                    if (groupCondition.isParen()){
                        sql = "("+sql+")";
                    }
                    n.getAttributes().put("MYSQL_SQL",sql);
                    return false;
                })
                .LRD(root,context);

        return root.getAttributes().get("MYSQL_SQL").toString();
    }

}
