package com.quitee.simplequery.parser;

import com.quitee.simplequery.ast.AstNode;
import com.quitee.simplequery.ast.AstNodeBaseFieldCondition;
import com.quitee.simplequery.ast.AstNodeGroupCondition;
import com.quitee.simplequery.ast.Token;

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
    public int[] next(Token token, Stack<Token> tokenStack, Stack<AstNode> nodeStack, QueryLexer lexer, Next next) {
        switch (token.getType()){
            case L_PAREN: {
                next.next(AstBuilder.GROUP_CONDITION, AstBuilder.FIELD_CONDITION);
                AstNode c = nodeStack.pop();
                int i = 0;
                while (c == flagEnd) {
                    i++;
                    c = nodeStack.pop();
                }
                if (c instanceof AstNodeGroupCondition) {
                    ((AstNodeGroupCondition) c).setParen(true);
                }

                nodeStack.push(c);

                while (i > 1) {
                    i--;
                    nodeStack.push(flagEnd);
                }
                return new int[]{AstBuilder.GROUP_CONDITION, AstBuilder.FIELD_CONDITION};
            }
            case R_PAREN:
                nodeStack.push(flagEnd);
                return null;
            case AND:
            case OR: {
                AstNode c1 = nodeStack.pop();
                next.next(AstBuilder.GROUP_CONDITION, AstBuilder.FIELD_CONDITION);
                AstNode c2 = nodeStack.pop();
                if (c2 == flagEnd){
                    c2 = nodeStack.pop();
                    AstNodeGroupCondition condition = new AstNodeGroupCondition(c1, token, c2);
                    nodeStack.push(condition);
                    nodeStack.push(flagEnd);
                    return null;
                }else {
                    AstNodeGroupCondition condition = new AstNodeGroupCondition(c1, token, c2);
                    nodeStack.push(condition);
                    return new int[]{AstBuilder.GROUP_CONDITION};
                }
            }
            default:
        }
        return new int[]{AstBuilder.GROUP_CONDITION};
    }

    private static class FlagParen implements AstNode{

        @Override
        public AstNode getLeft() {
            return null;
        }

        @Override
        public AstNode getRight() {
            return null;
        }
    }
}
