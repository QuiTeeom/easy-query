package com.quitee.simplequery.parser;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeGroupCondition implements AstNode{
    AstNode left;
    Token groupType;
    AstNode right;

    boolean paren;

    public AstNodeGroupCondition(AstNode left, Token groupType, AstNode right) {
        this.left = left;
        this.groupType = groupType;
        this.right = right;
    }

    @Override
    public String toString() {
        if (paren){
            return "( " + left + " " + groupType.getLiteral().getValue() + " " + right + " )";
        }else
            return left + " " + groupType.getLiteral().getValue() + " " + right;
    }

    public boolean isParen() {
        return paren;
    }

    public void setParen(boolean paren) {
        this.paren = paren;
    }

    public AstNode getLeft() {
        return left;
    }

    public Token getGroupType() {
        return groupType;
    }

    public AstNode getRight() {
        return right;
    }
}
