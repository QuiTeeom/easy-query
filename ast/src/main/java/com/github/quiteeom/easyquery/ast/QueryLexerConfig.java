package com.github.quiteeom.easyquery.ast;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class QueryLexerConfig {
    private final Map<String, TokenType> alias = new HashMap<>();

    private QueryLexerConfig() {
    }

    public Map<String, TokenType> getAlias() {
        return alias;
    }

    public QueryLexerConfig withAlias(String literal,TokenType tokenType){
        alias.put(literal,tokenType);
        return this;
    }

    public QueryLexerConfig withoutAlias(String literal){
        alias.remove(literal);
        return this;
    }

    public static QueryLexerConfig getInstance(){
        return initQueryConfig(new QueryLexerConfig());
    }

    public static QueryLexerConfig initQueryConfig(QueryLexerConfig config){
        config.withAlias("in",TokenType.IN)
                .withAlias("not in",TokenType.NOT_IN)
                .withAlias("contains",TokenType.CONTAINS)
                .withAlias("startWith",TokenType.START_WITH)
                .withAlias("endWith",TokenType.END_WITH)
                .withAlias("between",TokenType.BETWEEN)
                .withAlias("match",TokenType.MATCH)

                .withAlias("TRUE",TokenType.BOOL_TRUE)
                .withAlias("FALSE",TokenType.BOOL_FALSE)

                .withAlias("NULL",TokenType.NULL)
                .withAlias("null",TokenType.NULL)

                .withAlias("and",TokenType.AND)
                .withAlias("or",TokenType.OR)
                .withAlias("AND",TokenType.AND)
                .withAlias("OR",TokenType.OR);
        return config;
    }
}
