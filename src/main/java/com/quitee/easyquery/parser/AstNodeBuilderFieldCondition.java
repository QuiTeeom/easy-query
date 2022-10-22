package com.quitee.easyquery.parser;

import com.quitee.easyquery.ast.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeBuilderFieldCondition implements AstNodeBuilder{
    @Override
    public boolean support(Token token) {
        switch (token.getType()){
            case EQ:
            case NOT_EQ:

            case GT:
            case GT_EQ:
            case LT:
            case LT_EQ:

            case IN:
            case NOT_IN:
            case CONTAINS:
            case START_WITH:
            case END_WITH:
            case BETWEEN:
            case MATCH:
                return true;
            default:
                return false;
        }
    }

    @Override
    public int[] next(Token token, Stack<Token> tokenStack, Stack<AstNode> nodeStack, QueryLexer lexer, Next next) {
        switch (token.getType()){
            case EQ:
            case NOT_EQ:

            case GT:
            case GT_EQ:
            case LT:
            case LT_EQ:
            case CONTAINS:
            case START_WITH:
            case END_WITH:
            case MATCH:
                AstNode node = buildSimpleCompareNode(token,tokenStack.pop(),lexer.nextToken());
                nodeStack.push(node);
                break;
            case BETWEEN:
                AstNode bt = buildBetweenNode(token,tokenStack.pop(),lexer);
                nodeStack.push(bt);
                break;
            case IN:
            case NOT_IN:
                AstNode in = buildInNode(token,tokenStack.pop(),lexer);
                nodeStack.push(in);
                break;
        }
        return new int[]{AstBuilder.GROUP_CONDITION};
    }



    private AstNode buildSimpleCompareNode(Token operator,Token field,Token value){
        return new AstNodeCompareFieldCondition(field,operator,value);
    }

    private AstNode buildBetweenNode(Token operator,Token field,QueryLexer lexer){
        Token l = lexer.nextToken();
        Token f = lexer.nextToken();
        lexer.nextToken();
        Token t = lexer.nextToken();
        Token r = lexer.nextToken();

        return new AstNodeBetweenFieldCondition(field,operator,l,f,t,r);
    }

    private AstNode buildInNode(Token operator,Token field,QueryLexer lexer){
        Token t = lexer.nextToken();
        Token l = t;
        if (t.getType() != TokenType.L_PAREN){
            throw new UnExceptedTokenException(TokenType.L_PAREN,t);
        }
        List<Token> values = new ArrayList<>();

        t = lexer.nextToken();
        while (t!=null){
            if (t.getType()==TokenType.R_PAREN){
                return new AstNodeInFieldCondition(field,operator,values);
            }
            values.add(t);
            t = lexer.nextToken();
        }
        throw new MissionParenException(l);
    }

}
