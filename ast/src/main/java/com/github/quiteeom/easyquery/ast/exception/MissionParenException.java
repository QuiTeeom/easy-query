package com.github.quiteeom.easyquery.ast.exception;

import com.github.quiteeom.easyquery.ast.Token;

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
