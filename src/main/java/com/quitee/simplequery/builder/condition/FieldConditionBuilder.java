package com.quitee.simplequery.builder.condition;

import com.quitee.simplequery.builder.Query;
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

    public GroupConditionBuilder eq(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"=",getValue(o)));
    }

    public GroupConditionBuilder notEq(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"!=",getValue(o)));
    }

    public GroupConditionBuilder contains(String o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"contains",getValue(o)));
    }

    public GroupConditionBuilder startWith(String o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"startWith",getValue(o)));
    }

    public GroupConditionBuilder endWith(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"endWith",getValue(o)));
    }

    public GroupConditionBuilder in(Collection<?> collections){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"in",getValue(collections)));
    }

    public GroupConditionBuilder in(Object... objects){
        return Conditions.group(in(Arrays.asList(objects)));
    }

    public GroupConditionBuilder grateThan(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),">",getValue(o)));
    }

    public GroupConditionBuilder grateEqThan(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),">=",getValue(o)));
    }

    public GroupConditionBuilder lessThan(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"<",getValue(o)));
    }

    public GroupConditionBuilder lessEqThan(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"<=",getValue(o)));
    }

    private String getValue(Object o){
        return o.toString();
    }



    private static class FieldCondition implements Condition{
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
