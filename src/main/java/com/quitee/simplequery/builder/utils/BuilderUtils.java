package com.quitee.simplequery.builder.utils;

import java.util.Arrays;

/**
 * @author quitee
 * @date 2022/8/21
 */

public class BuilderUtils {

    public static String join(String... strings){
        StringBuilder res = new StringBuilder(strings[0]);

        for (int i=1;i<strings.length;i++){
            res.append(" ").append(strings[i]);
        }
        return res.toString();
    }
}
