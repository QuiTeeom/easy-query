package com.quitee.simplequery.parser;

/**
 * @author guhui
 * @date 2022/8/26
 */
public class QueryLexer {
    String str;
    int pos = 0;
    int max;

    char[] bc;

    int tokenStart = 0;
    int tokenEnd = 0;
    TokenType tokenType;

    char ch;

    static KeyWordTree KEY_WORDS = new KeyWordTree();
    static {
        KEY_WORDS.add("in",TokenType.IN)
                .add("not in",TokenType.NOT_IN)
                .add("contains",TokenType.CONTAINS)
                .add("startWith",TokenType.START_WITH)
                .add("endWith",TokenType.END_WITH)
                .add("between",TokenType.BETWEEN)
                .add("match",TokenType.MATCH)

                .add("and",TokenType.AND)
                .add("or",TokenType.OR);
    }


    public QueryLexer(String str) {
        this.str = str;
        max = str.length();
        bc = this.str.toCharArray();
    }

    public Token nextToken() {
        tokenType = null;

        for (; ; ) {
            next();

            switch (ch) {
                case '"': scanText();break;
                case '=':
                case '!':
                case '>':
                case '<': scanCompare();break;
                case '(': tokenStart = pos - 1;tokenEnd=tokenStart;tokenType = TokenType.L_PAREN;break;
                case ')': tokenStart = pos - 1;tokenEnd=tokenStart;tokenType = TokenType.R_PAREN;break;
                case ' ': break;
                case 0x1A: return null;
                default:
            }

            if ((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z')){
                scanWord();
            }

            if (ch>='0'&&ch<='9'){
                scanNumber();
            }

            if (tokenType!=null){
                return new Token(new Literal(new String(bc,tokenStart,tokenEnd+1-tokenStart),tokenStart,tokenEnd),tokenType);
            }

        }
    }

    private void scanNumber(){
        tokenStart = pos - 1;
        boolean allNumber = true;
        boolean comma = false;
        for (;;){
            next();
            if (ch>='0'&&ch<='9'){
                continue;
            }
            switch (ch){
                case '"':
                case '=':
                case '!':
                case '>':
                case '<':
                case '(':
                case ')':
                    pre();tokenEnd=pos-1;
                    if (!allNumber){
                        tokenType = TokenType.TEXT;
                    }else {
                        tokenType =TokenType.NUMBER;
                    }
                    return;

                case ' ':
                case 0x1A:
                    tokenEnd = pos-2;
                    if (!allNumber){
                        tokenType = TokenType.TEXT;
                    }else {
                        tokenType =TokenType.NUMBER;
                    }
                    return;
                case '.':
                    if (comma){
                        allNumber = false;
                    }else {
                        comma = true;
                    }
                    continue;
                default:
                    allNumber=false;
            }
        }
    }

    private void scanWord(){
        tokenStart = pos - 1;
        KeyWordTree keyWordTree = KEY_WORDS.next(ch);

        for (;;){
            next();
            switch (ch){
                case '"':
                case '=':
                case '!':
                case '>':
                case '<':
                case '(':
                case ')':
                    pre();
                    tokenEnd=pos-1;
                    if (keyWordTree==null||keyWordTree.getTokenType()==null){
                        tokenType = TokenType.TEXT;
                    }else {
                        tokenType = keyWordTree.getTokenType();
                    }
                    return;
                case ' ':
                case 0x1A:
                    tokenEnd = pos-2;
                    if (keyWordTree==null||keyWordTree.getTokenType()==null){
                        tokenType = TokenType.TEXT;
                    }else {
                        tokenType = keyWordTree.getTokenType();
                    }
                    return;
                default:
            }


            if (keyWordTree!=null){
                keyWordTree = keyWordTree.next(ch);
            }
        }
    }

    private void scanText() {
        tokenType = TokenType.TEXT;
        tokenStart = pos - 1;
        boolean trans = false;
        for (; ; ) {
            next();
            switch (ch) {
                case '"':
                case 0x1A:
                    tokenEnd = pos -1;
                    return;
                default:
                    break;
            }
        }
    }

    private void scanCompare() {
        tokenStart = pos - 1;

        switch (ch) {
            case '=':
                tokenType = TokenType.EQ;
                break;
            case '>':
                next();
                if (ch == '=') {
                    tokenType = TokenType.GT_EQ;
                } else {
                    tokenType = TokenType.GT;
                    pre();
                }
                break;
            case '<':
                next();
                if (ch == '=') {
                    tokenType = TokenType.LT_EQ;
                } else {
                    tokenType = TokenType.LT;
                    pre();
                }
                break;
            case '!':
                next();
                if (ch == '=') {
                    tokenType = TokenType.NOT_EQ;
                    break;
                }else {
                    throw new RuntimeException("语法错误:"+new String(str.getBytes(),0,pos));
                }
            default:
        }

        tokenEnd = pos - 1;
    }

    private void next() {
        try {
            ch = str.charAt(pos++);
        }catch (StringIndexOutOfBoundsException e){
            ch = 0x1A;
        }
    }

    private void pre() {
        ch = str.charAt(--pos);
    }
}
