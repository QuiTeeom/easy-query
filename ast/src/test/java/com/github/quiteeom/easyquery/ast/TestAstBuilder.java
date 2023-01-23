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
    }
}
