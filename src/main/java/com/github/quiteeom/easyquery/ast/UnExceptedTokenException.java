package com.github.quiteeom.easyquery.ast;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class UnExceptedTokenException extends RuntimeException{
    TokenType expected;
    Token current;

    public UnExceptedTokenException(TokenType expected, Token current) {
        this.expected = expected;
        this.current = current;
    }

    @Override
    public String getMessage() {
        if (current==null){
            return "missing TokenType. want:"+expected.toString();
        }

        return "unexpect TokenType:"+current.getLiteral()+". want:"+expected.toString();
    }
}
