package com.quitee.simplequery.parser;

import java.util.*;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstBuilder {

    public static final int FIELD_CONDITION = 0;
    public static final int GROUP_CONDITION = 1;

    static AstNodeBuilder[] builders = new AstNodeBuilder[]{
            new AstNodeBuilderFieldCondition(),
            new AstNodeBuilderGroupCondition()
    };




    public AstNode build(String query){
        QueryLexer queryLexer = new QueryLexer(query);
        Stack<AstNode> astNodeStack = new Stack<>();

        N n = new N(queryLexer,astNodeStack);
        n.next(FIELD_CONDITION,GROUP_CONDITION);

        return astNodeStack.pop();
    }

    private static class N implements AstNodeBuilder.Next{
        QueryLexer queryLexer;
        Stack<AstNode> astNodeStack;

        public N(QueryLexer queryLexer, Stack<AstNode> astNodeStack) {
            this.queryLexer = queryLexer;
            this.astNodeStack = astNodeStack;
        }

        @Override
        public void next(int... expects) {
            if (expects.length==0){
                return;
            }

            Stack<Token> tokenStack = new Stack<>();
            Token token;
            while ((token=queryLexer.nextToken())!=null){
                AstNodeBuilder support = null;
                for (int expect:expects){
                    if (builders[expect].support(token)){
                        support = builders[expect];
                        break;
                    }
                }

                if (support!=null){
                    support.next(token,tokenStack,astNodeStack,queryLexer,this);
                }else {
                    tokenStack.push(token);
                }
            }
        }
    }
}
