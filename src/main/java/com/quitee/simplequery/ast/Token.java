package com.quitee.simplequery.ast;

/**
 * @author guhui
 * @date 2022/8/26
 */
public class Token {
    TokenType type;
    Literal literal;

    public Token(Literal literal, TokenType type) {
        this.literal = literal;
        this.type = type;
    }

    public boolean isType(TokenType tokenType){
        return type==tokenType;
    }

    public TokenType getType() {
        return type;
    }

    public Literal getLiteral() {
        return literal;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", literal=" + literal +
                '}';
    }
}
