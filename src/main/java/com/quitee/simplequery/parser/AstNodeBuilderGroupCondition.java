package com.quitee.simplequery.parser;

import java.util.Stack;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeBuilderGroupCondition implements AstNodeBuilder{
    @Override
    public boolean support(Token token) {
        switch (token.getType()){
            case AND:
            case OR:
            case L_PAREN:
            case R_PAREN:
                return true;
            default:
                return false;
        }
    }

    FlagParen flagStart = new FlagParen();
    FlagParen flagEnd = new FlagParen();
    @Override
    public int[] next(Token token, Stack<Token> tokenStack, Stack<AstNode> nodeStack, QueryLexer lexer,Next next) {
        switch (token.getType()){
            case L_PAREN:
                nodeStack.push(flagStart);
                return new int[]{AstBuilder.GROUP_CONDITION,AstBuilder.FIELD_CONDITION};
            case R_PAREN:
                nodeStack.push(flagEnd);
                return null;
            case AND:
            case OR:
                AstNode c1 = nodeStack.pop();
                next.next(AstBuilder.GROUP_CONDITION,AstBuilder.FIELD_CONDITION);
                AstNode c2 = nodeStack.pop();
                boolean paren = false;
                if (c2 == flagEnd){
                    paren = true;
                    c2 = nodeStack.pop();
                    nodeStack.pop();
                }
                AstNodeGroupCondition condition = new AstNodeGroupCondition(c1,token,c2);
                condition.setParen(paren);
                nodeStack.push(condition);
        }
        return new int[]{AstBuilder.GROUP_CONDITION};
    }

    private static class FlagParen implements AstNode{

    }
}
