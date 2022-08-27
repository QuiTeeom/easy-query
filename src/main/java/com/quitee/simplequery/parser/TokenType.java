package com.quitee.simplequery.parser;

/**
 * @author guhui
 * @date 2022/8/26
 */
public enum TokenType {
    L_PAREN,
    R_PAREN,
    L_BECKET,
    R_BECKET,

    COMMA,

    EQ,
    NOT_EQ,
    GT,
    GT_EQ,
    LT,
    LT_EQ,

    IN,
    NOT_IN,
    CONTAINS,
    START_WITH,
    END_WITH,
    BETWEEN,
    MATCH,

    AND,
    OR,

    TEXT,
    NUMBER,
    OPERATOR,
    GROUP,

}
