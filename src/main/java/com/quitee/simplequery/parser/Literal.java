package com.quitee.simplequery.parser;

/**
 * @author guhui
 * @date 2022/8/25
 */
public class Literal {
    String value;
    int posStart;
    int posEnd;

    public Literal(String value, int posStart, int posEnd) {
        this.value = value;
        this.posStart = posStart;
        this.posEnd = posEnd;
    }

    public String getValue() {
        return value;
    }

    public int getPosStart() {
        return posStart;
    }

    public int getPosEnd() {
        return posEnd;
    }

    @Override
    public String toString() {
        return "Literal{" +
                "value='" + value + '\'' +
                ", posStart=" + posStart +
                ", posEnd=" + posEnd +
                '}';
    }
}

