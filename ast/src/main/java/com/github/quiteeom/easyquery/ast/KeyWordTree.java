package com.github.quiteeom.easyquery.ast;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/8/26
 */
public class KeyWordTree {

    Map<Character,KeyWordTree> slots = new HashMap<>(1<<8);

    KeyWord word = null;
    char ch;

    public KeyWordTree() {
    }

    public KeyWordTree(char ch) {
        this.ch = ch;
    }


    public KeyWordTree add(KeyWord keyWord){
        add(keyWord,0);
        return this;
    }


    public KeyWordTree add(String keyWord, TokenType tokenType){
        add(KeyWord.of(keyWord,tokenType));
        return this;
    }

    public KeyWordTree next(char ch){
        return slots.get(ch);
    }

    private void add(KeyWord keyWord,int pos){
        if (keyWord==null){
            return;
        }

        if (keyWord.isImportant()){
            throw new IllegalArgumentException("important key word is a leaf");
        }

        char ch = keyWord.getChars()[pos];

        KeyWordTree slot = slots.get(ch);
        if (slot==null){
            slot = new KeyWordTree(ch);
            slots.put(ch,slot);
        }
        pos++;
        if (pos<keyWord.length()){
            slot.add(keyWord,pos);
        }else {
            if (slot.word!=null){
                throw new IllegalArgumentException("duplicate key word"+keyWord);
            }
            slot.word = keyWord;
        }
    }


    public KeyWord getWord() {
        return word;
    }

    public TokenType getTokenType() {
        return word==null?null:word.getTokenType();
    }
}
