package com.github.quiteeom.easyquery.builder.condition;

/**
 * @author quitee
 * @date 2022/8/21
 */

public enum BasicConditionGroupType implements ConditionGroupType {
    AND("and"),
    OR("or")
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
