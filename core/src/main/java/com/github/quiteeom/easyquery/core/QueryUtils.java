package com.github.quiteeom.easyquery.core;

/**
 * @author quitee
 * @date 2022/8/21
 */

public class QueryUtils {

    public static String join(String... strings){
        StringBuilder res = new StringBuilder(strings[0]);

        for (int i=1;i<strings.length;i++){
            res.append(" ").append(strings[i]);
        }

        return res.toString();
    }
}
