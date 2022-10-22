package com.quitee.easyquery.ast;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class AstNodeValues implements AstNode{

    List<Token> values;

    public AstNodeValues(List<Token> values) {
        this.values = values;
    }

    @Override
    public AstNode getLeft() {
        return null;
    }

    @Override
    public AstNode getRight() {
        return null;
    }

    public List<Token> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return values.stream().map(Token::getLiteral).map(Literal::getValue).collect(Collectors.joining());
    }
}
