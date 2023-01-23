package com.github.quiteeom.easyquery.core;

/**
 * Theoretically, as long as it is a part of the query information, it can be described in words
 * @author quitee
 * @date 2022/8/21
 */

public interface Query {

    /**
     * convert the query to query string
     * @return query string
     */
    String toQueryString();
}
