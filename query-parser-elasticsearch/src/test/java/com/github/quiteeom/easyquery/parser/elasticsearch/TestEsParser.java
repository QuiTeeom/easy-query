package com.github.quiteeom.easyquery.parser.elasticsearch;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class TestEsParser {
    public static void main(String[] args) {
        System.out.println("((((name = \"quitee\" AND age between (18,24]) OR (name contains \"guagua\" AND age <= 18)) OR createDate < 2022-12-18T14:48:19.886) OR createDate = TRUE)");
        EsParser mysqlParser = new EsParser();
        QueryBuilder queryBuilder = mysqlParser.parseQuery("((((name = \"quitee\" AND age between (18,24]) OR (name contains \"guagua\" AND age <= 18)) OR createDate < 2022-12-18T14:48:19.886) OR createDate = TRUE)");
        System.out.println(new StringBuilder(queryBuilder.toString()));
    }
}
