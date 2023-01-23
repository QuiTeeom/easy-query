package com.github.quiteeom.easyquery.parser.elasticsearch;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.AstTraceFilters;
import com.github.quiteeom.easyquery.ast.AstTracer;
import com.github.quiteeom.easyquery.ast.node.AstNodeValue;
import com.github.quiteeom.easyquery.core.values.ArrayValue;
import com.github.quiteeom.easyquery.core.values.BaseValue;
import com.github.quiteeom.easyquery.core.values.BoundaryValue;
import com.github.quiteeom.easyquery.core.values.Value;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.quiteeom.easyquery.parser.elasticsearch.Constants.ES_VALUE_BETWEEN_FROM;
import static com.github.quiteeom.easyquery.parser.elasticsearch.Constants.ES_VALUE_BETWEEN_TO;


/**
 * @author quitee
 * @date 2023/1/23
 */

public class HandlerValue implements AstEsHandler, Function<AstNode,Boolean> {
    @Override
    public void applyAstTracer(AstTracer astTracer) {
        astTracer.withCallBack(AstTraceFilters.VALUE,this);
    }

    @Override
    public Boolean apply(AstNode astNode) {
        AstNodeValue<?> nodeValue = (AstNodeValue<?>) astNode;
        Object value = value2Sql(nodeValue.getValue());
        Helper.set(astNode,value);
        return false;
    }

    private Object value2Sql(Value value){
        switch (value.type()){
            case "datetime":
            case "string":
            case "number":
            case "bool":{
                BaseValue<?> baseValue = (BaseValue<?>) value;
                Helper.set(value,baseValue.getRaw());
                return baseValue.getRaw();
            }
            case "array": {
                ArrayValue arrayValue = (ArrayValue) value;
                List<Object> objects = arrayValue.getValues().stream()
                        .map(this::value2Sql)
                        .collect(Collectors.toList());
                Helper.set(value,objects);
                return objects;
            }
            case "boundary":{
                BoundaryValue boundaryValue = (BoundaryValue) value;
                Value from = boundaryValue.getFromValue();
                Value to = boundaryValue.getToValue();
                Object fromO = value2Sql(from);
                Object toO = value2Sql(to);
                value.getAttributes().put(ES_VALUE_BETWEEN_FROM,fromO);
                value.getAttributes().put(ES_VALUE_BETWEEN_TO,toO);
            }
            default:
                return null;
        }
    }
}
