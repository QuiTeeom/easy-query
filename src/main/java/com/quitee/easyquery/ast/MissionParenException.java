package com.quitee.easyquery.ast;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class MissionParenException extends RuntimeException{
    Token lParen;

    public MissionParenException(Token lParen ) {
        this.lParen = lParen;
    }

    @Override
    public String getMessage() {
            return "missing ')' for "+lParen.getLiteral();
    }
}
