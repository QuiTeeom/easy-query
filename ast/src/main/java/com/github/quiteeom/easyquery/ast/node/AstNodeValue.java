package com.github.quiteeom.easyquery.ast.node;

import com.github.quiteeom.easyquery.ast.AstNode;
import com.github.quiteeom.easyquery.core.values.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quitee
 * @date 2022/10/22
 */

public abstract class AstNodeValue<V extends Value> implements AstNode {

    V value;

    public AstNodeValue(V value) {
        this.value = value;
    }

    @Override
    public AstNode getLeft() {
        return null;
    }

    @Override
    public AstNode getRight() {
        return null;
    }

    Map<String,Object> attributes = new HashMap<>();
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public V getValue(){
        return value;
    }

    @Override
    public String toString() {
        return getValue().toQueryString();
    }
}
