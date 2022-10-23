package com.quitee.easyquery.ast;

import java.util.Arrays;

/**
 * @author quitee
 * @date 2022/8/27
 */

public class AstNodeBetweenFieldCondition extends AstNodeBaseFieldCondition {
    Object from;
    Object to;
    boolean fromInclude;
    boolean toInclude;

    public AstNodeBetweenFieldCondition(Token field, Token operator, Token left, Token from, Token to, Token right) {
        super(field, operator, Arrays.asList(left,from,to,right));
        this.from = from.getLiteral().toString();
        this.to = to.getLiteral().toString();
        this.fromInclude = left.getType() != TokenType.L_PAREN;
        this.toInclude = right.getType() != TokenType.L_PAREN;
    }

    public Object getFrom() {
        return from;
    }

    public Object getTo() {
        return to;
    }

    public boolean isFromInclude() {
        return fromInclude;
    }

    public boolean isToInclude() {
        return toInclude;
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
