package com.github.quiteeom.easyquery.ast;

/**
 * @author quitee
 * @date 2023/1/29
 */

public class KeyWord {
    private final String word;
    private final boolean important;

    private final TokenType tokenType;

    private final char[] chars;

    private final int length;

    public KeyWord(String word, TokenType tokenType, boolean important) {
        this.word = word;
        this.important = important;
        this.tokenType = tokenType;
        this.chars = word.toCharArray();
        this.length = chars.length;
    }

    public KeyWord(String word, TokenType tokenType) {
        this(word, tokenType, false);
    }

    public char[] getChars() {
        return chars;
    }

    public String getWord() {
        return word;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public boolean isImportant() {
        return important;
    }

    public int length(){
        return length;
    }

    @Override
    public String toString() {
        return word;
    }

    public static KeyWord of(String word,TokenType tokenType){
        return new KeyWord(word,tokenType,false);
    }

    public static KeyWord important(String word,TokenType tokenType){
        return new KeyWord(word,tokenType,true);
    }
}
