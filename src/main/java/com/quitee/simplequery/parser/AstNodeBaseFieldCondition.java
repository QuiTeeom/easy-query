package com.quitee.simplequery.parser;

import java.util.List;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeBaseFieldCondition implements AstNode{
    Token field;
    Token operator;
    List<Token> values;

    public AstNodeBaseFieldCondition(Token field, Token operator, List<Token> values) {
        this.field = field;
        this.operator = operator;
        this.values = values;
    }

    public Token getField() {
        return field;
    }

    public Token getOperator() {
        return operator;
    }

    public List<Token> getValues() {
        return values;
    }
}
