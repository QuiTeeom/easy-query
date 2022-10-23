package com.quitee.easyquery.parser;

import com.quitee.easyquery.ast.TokenType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class QueryLexerConfig {
    Map<String, TokenType> alias;

    public QueryLexerConfig() {
        alias = new HashMap<>();
    }

    public Map<String, TokenType> getAlias() {
        return alias;
    }

    public QueryLexerConfig withAlias(String literal,TokenType tokenType){
        alias.put(literal,tokenType);
        return this;
    }

    public static QueryLexerConfig getInstance(){
        return new QueryLexerConfig();
    }
}
