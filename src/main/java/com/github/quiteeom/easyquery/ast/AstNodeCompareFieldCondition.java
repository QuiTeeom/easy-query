package com.github.quiteeom.easyquery.ast;

import java.util.Collections;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeCompareFieldCondition extends AstNodeBaseFieldCondition {
    public AstNodeCompareFieldCondition(Token field, Token operator, Token value) {
        super(field, operator, Collections.singletonList(value));
    }

    @Override
    public String toString() {
        return getField().getLiteral().getValue() + " " + getOperator().getLiteral().getValue() + " " + getValues().get(0).getLiteral().getValue();
    }
}
