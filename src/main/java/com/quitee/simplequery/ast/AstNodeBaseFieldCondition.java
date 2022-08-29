package com.quitee.simplequery.ast;

import java.util.List;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeBaseFieldCondition implements AstNode {
    AstNodeField field;
    Token operator;
    AstNodeValues values;

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
}
