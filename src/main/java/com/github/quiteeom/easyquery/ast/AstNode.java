package com.github.quiteeom.easyquery.ast;

import java.util.Map;

/**
 * @author quitee
 * @date 2022/8/27
 */

public interface AstNode {

    AstNode getLeft();

    AstNode getRight();

    Map<String,Object> getAttributes();
}
