package com.quitee.simplequery.parser;

import java.util.Stack;

/**
 * @author quitee
 * @date 2022/8/27
 */

public interface AstNodeBuilder {

    boolean support(Token token);

    void next(Token token, Stack<Token> tokenStack, Stack<AstNode> nodeStack, QueryLexer lexer,Next next);

    interface Next{
        void next(int... expects);
    }
}
