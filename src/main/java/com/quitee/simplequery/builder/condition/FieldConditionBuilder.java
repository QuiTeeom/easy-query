package com.quitee.simplequery.builder.condition;

import com.quitee.simplequery.builder.utils.BuilderUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author quitee
 * @date 2022/8/21
 */

public class FieldConditionBuilder {
    FieldNameProvider fieldNameProvider;

    public FieldConditionBuilder(String filedName) {
        this.fieldNameProvider = new DirectFieldName(filedName);
    }

    public FieldConditionBuilder(FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }

    public Condition eq(Object o){
        return new FieldCondition(fieldNameProvider.getName(),"=",getValue(o));
    }

    public Condition notEq(Object o){
        return new FieldCondition(fieldNameProvider.getName(),"!=",getValue(o));
    }

    public Condition contains(String o){
        return new FieldCondition(fieldNameProvider.getName(),"contains",getValue(o));
    }

    public Condition startWith(String o){
        return new FieldCondition(fieldNameProvider.getName(),"startWith",getValue(o));
    }

    public Condition endWith(Object o){
        return new FieldCondition(fieldNameProvider.getName(),"endWith",getValue(o));
    }

    public Condition in(Collection<?> collections){
        return new FieldCondition(fieldNameProvider.getName(),"in",getValue(collections));
    }

    public Condition in(Object... objects){
        return in(Arrays.asList(objects));
    }

    private String getValue(Object o){
        return o.toString();
    }



    private static class FieldCondition implements Condition {
        String filedName;

        String compare;

        String value;

        public FieldCondition(String filedName, String compare, String value) {
            this.filedName = filedName;
            this.compare = compare;
            this.value = value;
        }

        @Override
        public String toString() {
            return BuilderUtils.join(filedName,compare,value);
        }
    }

    private static class DirectFieldName implements FieldNameProvider{
        String name;

        public DirectFieldName(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
