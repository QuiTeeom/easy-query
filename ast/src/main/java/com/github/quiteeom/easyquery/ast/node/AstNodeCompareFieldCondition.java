package com.github.quiteeom.easyquery.ast.node;

import com.github.quiteeom.easyquery.ast.Token;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeCompareFieldCondition extends AstNodeBaseFieldCondition {
    public AstNodeCompareFieldCondition(Token field, Token operator, AstNodeValue<?> value) {
        super(field, operator, value);
    }
}
