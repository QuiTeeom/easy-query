package com.github.quiteeom.easyquery.ast;

/**
 * @author quitee
 * @date 2023/1/29
 */

public class AstBuilderConfig {
    private final QueryLexerConfig lexerConfig = QueryLexerConfig.getInstance();

    public QueryLexerConfig getLexerConfig() {
        return lexerConfig;
    }
}
