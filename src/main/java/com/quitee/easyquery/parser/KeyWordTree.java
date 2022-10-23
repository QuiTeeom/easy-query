package com.quitee.easyquery.parser;

import com.quitee.easyquery.ast.TokenType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/8/26
 */
public class KeyWordTree {

    Map<Character,KeyWordTree> slots = new HashMap<>(1<<8);

    String word = null;
    TokenType tokenType;
    char ch;

    public KeyWordTree() {
    }

    public KeyWordTree(char ch) {
        this.ch = ch;
    }

    public KeyWordTree add(String keyWord, TokenType tokenType){
        add(keyWord.toCharArray(),0,tokenType);
        return this;
    }

    public KeyWordTree next(char ch){
        return slots.get(ch);
    }

    private void add(char[] chars,int pos,TokenType tokenType){
        KeyWordTree slot = slots.get(chars[pos]);
        if (slot==null){
            slot = new KeyWordTree(chars[pos]);
            slots.put(chars[pos],slot);
        }
        pos++;
        if (pos<chars.length){
            slot.add(chars,pos,tokenType);
        }else {
            slot.word = new String(chars);
            slot.tokenType = tokenType;
        }
    }

    public String getWord() {
        return word;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
