package com.quitee.easyquery.ast;

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

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token.getLiteral().getValue();
    }
}
