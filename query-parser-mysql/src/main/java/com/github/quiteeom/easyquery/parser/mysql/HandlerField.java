package com.github.quiteeom.easyquery.parser.mysql;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.ast.AstTraceFilters;
import com.github.quiteeom.easyquery.ast.AstTracer;
import com.github.quiteeom.easyquery.ast.node.AstNodeField;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author quitee
 * @date 2023/1/23
 */

public class HandlerField implements AstMysqlHandler, Function<AstNode,Boolean> {
    @Override
    public void applyAstTracer(AstTracer astTracer) {
        astTracer.withCallBack(AstTraceFilters.FIELD,this);
    }

    @Override
    public Boolean apply(AstNode astNode) {
        AstNodeField nodeField = (AstNodeField) astNode;
        String field = nodeField.toString();
        String fieldSql = "`"+camelToUnderline(field)+"`";
        Helper.setSql(astNode,fieldSql);
        return false;
    }

    private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z0-9]");
    public static String camelToUnderline(String line){
        Matcher matcher = HUMP_PATTERN.matcher(line);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
