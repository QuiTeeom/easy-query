package com.quitee.simplequery.builder.condition;

/**
 * @author quitee
 * @date 2022/8/21
 */

public enum BasicConditionGroupType implements ConditionGroupType {
    AND("AND"),
    OR("OR")
    ;

    String type;

    BasicConditionGroupType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }
}
