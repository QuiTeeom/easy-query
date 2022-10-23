package com.github.quiteeom.easyquery.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeBaseFieldCondition implements AstNode {
    AstNodeField field;
    Token operator;
    AstNodeValues values;

    Map<String,Object> attributes = new HashMap<>();

    public AstNodeBaseFieldCondition(Token field, Token operator, List<Token> values) {
        this.field = new AstNodeField(field);
        this.operator = operator;
        this.values = new AstNodeValues(values);
    }

    public Token getField() {
        return field.getToken();
    }

    public Token getOperator() {
        return operator;
    }

    public List<Token> getValues() {
        return values.getValues();
    }

    @Override
    public AstNode getLeft() {
        return field;
    }

    @Override
    public AstNode getRight() {
        return values;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
