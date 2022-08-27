package com.quitee.simplequery.parser;

/**
 * @author guhui
 * @date 2022/8/26
 */
public class Tokens {


    public static Token getToken(Literal literal){
        switch (literal.value){
            case "(": return new Token(literal,TokenType.L_PAREN);
            case ")": return new Token(literal,TokenType.R_PAREN);


            case "=": return new Token(literal,TokenType.EQ);
            case "!=": return new Token(literal,TokenType.NOT_EQ);


            default: return new Token(literal,TokenType.TEXT);
        }
    }

}
