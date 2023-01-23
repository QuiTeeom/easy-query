package com.github.quiteeom.easyquery.ast;

import com.github.quiteeom.easyquery.ast.exception.SyntaxException;

/**
 * @author quitee
 * @date 2022/8/26
 */
public class QueryLexer {
    String str;
    int pos = 0;
    int max;

    char[] bc;

    char[] raw_bc;

    int tokenStart = 0;
    int tokenEnd = 0;
    TokenType tokenType;

    char ch;

    KeyWordTree keyWordTree;

    static KeyWordTree KEY_WORDS = defaultKeyWordTree();

    private static KeyWordTree defaultKeyWordTree(){
        return new KeyWordTree().add("in",TokenType.IN)
                .add("not in",TokenType.NOT_IN)
                .add("contains",TokenType.CONTAINS)
                .add("startWith",TokenType.START_WITH)
                .add("endWith",TokenType.END_WITH)
                .add("between",TokenType.BETWEEN)
                .add("match",TokenType.MATCH)

                .add("TRUE",TokenType.BOOL_TRUE)
                .add("FALSE",TokenType.BOOL_FALSE)

                .add("and",TokenType.AND)
                .add("or",TokenType.OR)
                .add("AND",TokenType.AND)
                .add("OR",TokenType.OR);
    }


    public QueryLexer(String str) {
        this.str = str;
        max = str.length();
        raw_bc = this.str.toCharArray();
        bc = this.str.toCharArray();
        keyWordTree = KEY_WORDS;
    }

    public QueryLexer(String str, QueryLexerConfig config) {
        this.str = str;
        max = str.length();
        bc = this.str.toCharArray();
        if (config.getAlias()!=null){
            keyWordTree = defaultKeyWordTree();
            config.getAlias().forEach((t,ty)->{
                keyWordTree.add(t,ty);
            });
        }
    }

    public Token nextToken() {
        tokenType = null;

        for (; ; ) {
            next();

            switch (ch) {
                case '"':
                    // 处理文字
                    scanText();
                    break;
                case '=':
                case '!':
                case '>':
                case '<':
                    // 直接处理比较运算符号，比起自定义的，可以无需后面的空格
                    // a>0 也可以去识别
                    scanCompare();
                    break;
                case '(': tokenStart = pos - 1;tokenEnd=tokenStart;tokenType = TokenType.L_PAREN;break;
                case ')': tokenStart = pos - 1;tokenEnd=tokenStart;tokenType = TokenType.R_PAREN;break;
                case '[': tokenStart = pos - 1;tokenEnd=tokenStart;tokenType = TokenType.L_BECKET;break;
                case ']': tokenStart = pos - 1;tokenEnd=tokenStart;tokenType = TokenType.R_BECKET;break;
                case ',': tokenStart = pos - 1;tokenEnd=tokenStart;tokenType = TokenType.COMMA;break;
                case ' ':
                case '\t':
                case '\r':
                case '\n':
                    break;
                case 0x1A: return null;
                default:
                    scanWord();
            }


            if (tokenType!=null){
                return new Token(new Literal(new String(bc,tokenStart,tokenEnd+1-tokenStart),tokenStart,tokenEnd),tokenType);
            }

        }
    }

    private void scanWord(){
        tokenStart = pos - 1;
        KeyWordTree keyWordTree = this.keyWordTree.next(ch);

        for (;;){
            next();

            switch (ch){
                case '=':
                case '!':
                case '>':
                case '<':
                case '(':
                case ')':
                case '[':
                case ']':
                case ',':
                    // 如果时关键的字，就断开
                    pre();
                    tokenEnd=pos-1;
                    if (keyWordTree==null||keyWordTree.getTokenType()==null){
                        tokenType = TokenType.TEXT;
                    }else {
                        tokenType = keyWordTree.getTokenType();
                    }
                    return;
                case ' ':
                    if (keyWordTree!=null){
                        KeyWordTree test = keyWordTree.next(ch);
                        if (test!=null){
                            keyWordTree = test;
                            continue;
                        }
                    }else {
                        tokenEnd = pos-2;
                        tokenType = TokenType.TEXT;
                        return;
                    }
                case 0x1A:
                    if (keyWordTree==null||keyWordTree.getTokenType()==null){
                        tokenEnd = pos-1;
                        tokenType = TokenType.TEXT;
                        return;
                    }else {
                        tokenEnd = pos-2;
                        tokenType = keyWordTree.getTokenType();
                        return;
                    }
                default:
            }

            if (keyWordTree!=null){
                keyWordTree = keyWordTree.next(ch);
            }
        }
    }

    private void scanText() {
        tokenType = TokenType.STRING;
        tokenStart = pos - 1;
        for (; ; ) {
            next();
            switch (ch) {
                case '"':
                case 0x1A:
                    tokenEnd = pos -1;
                    return;
                case '\\':
                    // 如果时转义符，直接跳过去就行了
                    next();
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
                    throw new SyntaxException("语法错误:"+new String(str.getBytes(),0,pos));
                }
            default:
        }

        tokenEnd = pos - 1;
    }

    private void next() {
        if (ch == 0x1A){
            return;
        }
        try {
            ch = str.charAt(pos);
        }catch (StringIndexOutOfBoundsException e){
            ch = 0x1A;
            return;
        }
        pos++;
    }

    private void pre() {
        ch = str.charAt(--pos);
    }
}
