package com.github.quiteeom.easyquery.ast;

import com.github.quiteeom.easyquery.ast.exception.MissionParenException;
import com.github.quiteeom.easyquery.ast.exception.SyntaxException;
import com.github.quiteeom.easyquery.ast.exception.UnExceptedTokenException;
import com.github.quiteeom.easyquery.ast.node.AstNodeCompareFieldCondition;
import com.github.quiteeom.easyquery.ast.node.SimpleAstNodeValue;
import com.github.quiteeom.easyquery.core.values.ArrayValue;
import com.github.quiteeom.easyquery.core.values.BoundaryValue;
import com.github.quiteeom.easyquery.core.values.Value;

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


    DefaultValueConvertor defaultValueConvertor = new DefaultValueConvertor();

    private AstNode buildSimpleCompareNode(Token operator,Token field,Token valueToken){
        Value value = defaultValueConvertor.convert(valueToken);
        return new AstNodeCompareFieldCondition(field,operator,new SimpleAstNodeValue<>(value,valueToken));
    }

    private AstNode buildBetweenNode(Token operator,Token field,QueryLexer lexer){
        Token l = lexer.nextToken();
        Token f = lexer.nextToken();
        Token c = lexer.nextToken();
        Token t = lexer.nextToken();
        Token r = lexer.nextToken();
        if (l==null||(l.getType()!=TokenType.L_PAREN&&l.getType()!=TokenType.L_BECKET)){
            throw new SyntaxException("语法错误:"+l);
        }
        if (c==null||c.getType()!=TokenType.COMMA){
            throw new SyntaxException("语法错误:"+c);
        }
        if (r==null||(r.getType()!=TokenType.R_PAREN&&r.getType()!=TokenType.R_BECKET)){
            throw new SyntaxException("语法错误:"+r);
        }

        if (f==null){
            throw new SyntaxException("语法错误:from is null");
        }

        if (t==null){
            throw new SyntaxException("语法错误:to is null");
        }

        BoundaryValue boundaryValue = new BoundaryValue(
                l.getType()==TokenType.L_PAREN?BoundaryValue.OPEN:BoundaryValue.CLOSE,
                defaultValueConvertor.convert(f),
                defaultValueConvertor.convert(t),
                r.getType()==TokenType.R_PAREN? BoundaryValue.OPEN: BoundaryValue.CLOSE
        );
        return new AstNodeCompareFieldCondition(field,operator,new SimpleAstNodeValue<>(boundaryValue,l,f,c,t,r));
    }

    private AstNode buildInNode(Token operator,Token field,QueryLexer lexer){
        List<Token> tokens = new ArrayList<>();

        Token t = lexer.nextToken();
        Token l = t;
        if (t==null||t.getType() != TokenType.L_PAREN){
            throw new UnExceptedTokenException(TokenType.L_PAREN,t);
        }
        tokens.add(l);

        List<Value> values = new ArrayList<>();

        t = lexer.nextToken();
        while (t!=null){
            tokens.add(t);
            if (t.getType()==TokenType.R_PAREN){
                return new AstNodeCompareFieldCondition(field,operator,new SimpleAstNodeValue<>(new ArrayValue(values),tokens));
            }else if (t.getType()!= TokenType.COMMA){
                values.add(defaultValueConvertor.convert(t));
            }
            t = lexer.nextToken();
        }

        throw new MissionParenException(l);
    }

}
