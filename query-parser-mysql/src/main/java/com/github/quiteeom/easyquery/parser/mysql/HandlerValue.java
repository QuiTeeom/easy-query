package com.github.quiteeom.easyquery.parser.mysql;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.AstTraceFilters;
import com.github.quiteeom.easyquery.ast.AstTracer;
import com.github.quiteeom.easyquery.ast.node.AstNodeValue;
import com.github.quiteeom.easyquery.core.values.ArrayValue;
import com.github.quiteeom.easyquery.core.values.RangeValue;
import com.github.quiteeom.easyquery.core.values.Value;
import com.github.quiteeom.easyquery.core.values.Values;

import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.quiteeom.easyquery.parser.mysql.Constants.MYSQL_VALUE_BETWEEN_FROM;
import static com.github.quiteeom.easyquery.parser.mysql.Constants.MYSQL_VALUE_BETWEEN_TO;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class HandlerValue implements AstMysqlHandler, Function<AstNode,Boolean> {
    @Override
    public void applyAstTracer(AstTracer astTracer) {
        astTracer.withCallBack(AstTraceFilters.VALUE,this);
    }

    @Override
    public Boolean apply(AstNode astNode) {
        AstNodeValue<?> nodeValue = (AstNodeValue<?>) astNode;
        String field = value2Sql(nodeValue.getValue());
        Helper.setSql(astNode,field);
        return false;
    }

    private String value2Sql(Value value){
        switch (value.type()){
            case Values.TYPE_DATE_TIME:{
                String sqlStr = "'"+value.toQueryString()+"'";
                Helper.setSql(value,sqlStr);
                return sqlStr;
            }
            case Values.TYPE_STRING:{
                String queryStr = value.toQueryString();
                String sqlStr = "'"+queryStr.substring(1,queryStr.length()-1)+"'";
                Helper.setSql(value,sqlStr);
                return sqlStr;
            }
            case Values.TYPE_NUMBER:
            case Values.TYPE_BOOL:{
                String sqlStr = value.toQueryString();
                Helper.setSql(value,sqlStr);
                return sqlStr;
            }
            case Values.TYPE_ARRAY: {
                ArrayValue arrayValue = (ArrayValue) value;
                String arraySqlStr = "("+arrayValue.getValues().stream()
                        .map(this::value2Sql)
                        .collect(Collectors.joining(","))+")";
                Helper.setSql(value,arraySqlStr);
                return arraySqlStr;
            }
            case Values.TYPE_RANGE:{
                RangeValue rangeValue = (RangeValue) value;
                Value from = rangeValue.getFromValue();
                Value to = rangeValue.getToValue();
                String fromSqlStr = value2Sql(from);
                String toSqlStr = value2Sql(to);
                value.getAttributes().put(MYSQL_VALUE_BETWEEN_FROM,fromSqlStr);
                value.getAttributes().put(MYSQL_VALUE_BETWEEN_TO,toSqlStr);
            }
            default:
                return null;
        }
    }
}
