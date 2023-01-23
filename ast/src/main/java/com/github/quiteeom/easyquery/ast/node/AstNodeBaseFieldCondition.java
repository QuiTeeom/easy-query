package com.github.quiteeom.easyquery.ast.node;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeBaseFieldCondition implements AstNode {
    AstNodeField left;
    Token operator;
    AstNodeValue<?> right;

    Map<String,Object> attributes = new HashMap<>();

    public AstNodeBaseFieldCondition(Token field, Token operator, AstNodeValue<?> value) {
        this.left = new AstNodeField(field);
        this.operator = operator;
        this.right = value;
    }

    public Token getField() {
        return left.getToken();
    }

    public Token getOperator() {
        return operator;
    }

    public AstNodeValue<?> getValue() {
        return right;
    }

    @Override
    public AstNode getLeft() {
        return left;
    }

    @Override
    public AstNode getRight() {
        return right;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return getField().getLiteral().getValue() + " " + getOperator().getLiteral().getValue() + " " + getValue().toString();
    }
}
