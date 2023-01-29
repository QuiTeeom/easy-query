package com.github.quiteeom.easyquery.ast;

/**
 * @author quitee
 * @date 2022/12/18
 */

public class TestAstBuilder {
    public static void main(String[] args) {
        AstBuilder astBuilder = new AstBuilder();
        AstNode node = astBuilder.build("((((name = \"quitee\" AND age between (18,24]) OR (name contains \"guagua\" AND age <= 18)) OR createDate < 2022-12-18T14:48:19.886) OR createDate = TRUE)");
        System.out.println(node);

        node = astBuilder.build("(name = \"quitee\" AND age between (18,24]) OR (name contains \"guagua\" AND age <= 18)");
//        System.out.println(JSON.toJSONString(node, SerializerFeature.PrettyFormat));



        astBuilder = new AstBuilder();
        astBuilder.getLexerConfig().getAlias().put("是",TokenType.EQ);
        astBuilder.getLexerConfig().getAlias().put("不是",TokenType.NOT_EQ);
        astBuilder.getLexerConfig().getAlias().put("小于",TokenType.LT);

        astBuilder.getLexerConfig().getAlias().put("或",TokenType.OR);
        astBuilder.getLexerConfig().getAlias().put("且",TokenType.AND);

        AstNode root = astBuilder.build("name 是 \"quitee\" 且 age 小于 18");
        System.out.println(root);
    }
}
