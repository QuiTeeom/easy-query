package com.quitee.easyquery.ast;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeInFieldCondition extends AstNodeBaseFieldCondition {

    public AstNodeInFieldCondition(Token field, Token operator, List<Token> values) {
        super(field, operator, values);
    }

    @Override
    public String toString() {
        return getField().getLiteral().getValue() + " " + getOperator().getLiteral().getValue() + " ( "+
                getValues().stream().map(Token::getLiteral).map(Literal::getValue).collect(Collectors.joining(","))
                +" ) ";
    }
}
