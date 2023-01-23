package com.github.quiteeom.easyquery.ast.node;

import com.github.quiteeom.easyquery.ast.Token;
import com.github.quiteeom.easyquery.ast.AstNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeGroupCondition implements AstNode {
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
        }else {
            return left + " " + groupType.getLiteral().getValue() + " " + right;
        }
    }

    public boolean isParen() {
        return paren;
    }

    public void setParen(boolean paren) {
        this.paren = paren;
    }

    public Token getGroupType() {
        return groupType;
    }

    @Override
    public AstNode getLeft() {
        return left;
    }

    @Override
    public AstNode getRight() {
        return right;
    }

    Map<String,Object> attributes = new HashMap<>();
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
