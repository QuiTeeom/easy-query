package com.github.quiteeom.easyquery.parser.mysql;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class TestMysqlParser {
    public static void main(String[] args) {
        System.out.println("((((name = \"quitee\" AND age between (18,24]) OR (name contains \"guagua\" AND age <= 18)) OR createDate < 2022-12-18T14:48:19.886) OR createDate = TRUE OR class in (1,23,\"dd\"))");
        MysqlParser mysqlParser = new MysqlParser();
        String sql = mysqlParser.parseQuery("((((name = \"quitee\" AND age between (18,24]) OR (name contains \"guagua\" AND age <= 18)) OR createDate < 2022-12-18T14:48:19.886) OR createDate = TRUE) OR class not in (1,23,\"dd\")");
        System.out.println(sql);
    }
}
