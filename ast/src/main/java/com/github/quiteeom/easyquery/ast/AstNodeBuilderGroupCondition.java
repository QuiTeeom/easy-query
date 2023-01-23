package com.github.quiteeom.easyquery.ast;

import com.github.quiteeom.easyquery.ast.node.AstNodeGroupCondition;
import com.github.quiteeom.easyquery.ast.exception.MissionParenException;

import java.util.Map;
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
            // 如果是 ( 获取下一个节点
            case L_PAREN: {
                next.next(AstBuilder.GROUP_CONDITION, AstBuilder.FIELD_CONDITION);
                AstNode c = nodeStack.pop();
                // c 只可能是 group，或者field
                if (c==flagEnd){
                    c = nodeStack.pop();
                }else {
                    throw new MissionParenException(token);
                }
                if (c instanceof AstNodeGroupCondition){
                    AstNodeGroupCondition groupCondition = (AstNodeGroupCondition) c;
                    groupCondition.setParen(true);
                    nodeStack.push(c);
                    return new int[]{AstBuilder.GROUP_CONDITION};
                }else {
                    nodeStack.push(c);
                    return new int[]{AstBuilder.GROUP_CONDITION};
                }
            }
            case R_PAREN:
                nodeStack.push(flagEnd);
                // 如果是 )
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

        @Override
        public Map<String, Object> getAttributes() {
            return null;
        }
    }
}
