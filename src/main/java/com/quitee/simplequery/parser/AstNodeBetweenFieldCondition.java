package com.quitee.simplequery.parser;

import java.util.Arrays;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeBetweenFieldCondition extends AstNodeBaseFieldCondition{

    public AstNodeBetweenFieldCondition(Token field, Token operator, Token left,Token from,Token to,Token right) {
        super(field, operator, Arrays.asList(left,from,to,right));
    }

    @Override
    public String toString() {
        return getField().getLiteral().getValue() + " " + getOperator().getLiteral().getValue() + " "
                + getValues().get(0).getLiteral().getValue()
                + getValues().get(1).getLiteral().getValue()
                + ","
                + getValues().get(2).getLiteral().getValue()
                + getValues().get(3).getLiteral().getValue()
                ;
    }
}
