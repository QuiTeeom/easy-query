package com.github.quiteeom.easyquery.ast.node;

import com.github.quiteeom.easyquery.ast.Token;
import com.github.quiteeom.easyquery.core.values.Value;

import java.util.Arrays;
import java.util.List;

/**
 * @author quitee
 * @date 2022/12/18
 */

public class SimpleAstNodeValue<V extends Value> extends AstNodeValue<V>{
    List<Token> tokens;

    public SimpleAstNodeValue(V value, Token... token) {
        super(value);
        this.tokens = Arrays.asList(token);
    }

    public SimpleAstNodeValue(V value, List<Token> tokens) {
        super(value);
        this.tokens = tokens;
    }
}
