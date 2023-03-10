package com.github.quiteeom.easyquery.ast;

import java.util.Stack;

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

    private final AstBuilderConfig config;

    public AstBuilder(AstBuilderConfig config) {
        this.config = config;
    }

    public AstBuilder() {
        this.config = new AstBuilderConfig();
    }

    public AstNode build(String query){
        QueryLexer queryLexer = new QueryLexer(query,config.getLexerConfig());
        Stack<AstNode> astNodeStack = new Stack<>();

        N n = new N(config, queryLexer,astNodeStack);
        n.next(FIELD_CONDITION,GROUP_CONDITION);

        return astNodeStack.pop();
    }

    public AstBuilderConfig getConfig() {
        return config;
    }

    private static class N implements AstNodeBuilder.Next{
        AstBuilderConfig config;
        QueryLexer queryLexer;
        Stack<AstNode> astNodeStack;

        public N(AstBuilderConfig config, QueryLexer queryLexer, Stack<AstNode> astNodeStack) {
            this.config = config;
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
                    int[] nextExpects = support.next(token,tokenStack,astNodeStack,queryLexer,this);
                    if (nextExpects!=null){
                        next(nextExpects);
                    }
                    return;
                }else {
                    tokenStack.push(token);
                }
            }
        }
    }
}
