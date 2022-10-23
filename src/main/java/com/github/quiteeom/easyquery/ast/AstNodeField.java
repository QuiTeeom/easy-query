package com.github.quiteeom.easyquery.ast;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class AstNodeField implements AstNode{
    Token token;

    public AstNodeField(Token field) {
        this.token = field;
    }

    @Override
    public AstNode getLeft() {
        return null;
    }

    @Override
    public AstNode getRight() {
        return null;
    }

    Map<String,Object> attributes = new HashMap<>();
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token.getLiteral().getValue();
    }
}
