package com.github.quiteeom.easyquery.builder.condition;

import com.github.quiteeom.easyquery.builder.condition.valueconverter.Converters;
import com.github.quiteeom.easyquery.builder.utils.BuilderUtils;

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

    /**
     * =
     * @param o value
     * @return builder
     */
    public GroupConditionBuilder eq(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"=",getValue(o)));
    }

    /**
     * !=
     * @param o value
     * @return builder
     */
    public GroupConditionBuilder notEq(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"!=",getValue(o)));
    }

    /**
     * >
     * @param o value
     * @return builder
     */
    public GroupConditionBuilder gt(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),">",getValue(o)));
    }

    /**
     * >=
     * @param o value
     * @return builder
     */
    public GroupConditionBuilder gtOrEq(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),">=",getValue(o)));
    }

    /**
     * <
     * @param o value
     * @return builder
     */
    public GroupConditionBuilder lt(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"<",getValue(o)));
    }

    /**
     * <=
     * @param o value
     * @return builder
     */
    public GroupConditionBuilder ltOrEq(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"<=",getValue(o)));
    }

    /**
     * in
     * @param collections values
     * @return builder
     */
    public GroupConditionBuilder in(Collection<?> collections){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"in",getValue(collections)));
    }

    /**
     * in
     * @param objects values
     * @return builder
     */
    public GroupConditionBuilder in(Object... objects){
        return Conditions.group(in(Arrays.asList(objects)));
    }

    /**
     * contains
     * @param o value
     * @return builder
     */
    public GroupConditionBuilder contains(String o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"contains",getValue(o)));
    }

    /**
     * startWith
     * @param o value
     * @return builder
     */
    public GroupConditionBuilder startWith(String o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"startWith",getValue(o)));
    }


    /**
     * endWith
     * @param o value
     * @return builder
     */
    public GroupConditionBuilder endWith(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"endWith",getValue(o)));
    }

    /**
     * between
     * @param from from
     * @param fromEdge fromEdge, [ or (
     * @param to to
     * @param  toEdge toEdge, ] or )
     * @return builder
     */
    public GroupConditionBuilder between(Object from, Edge fromEdge, Object to,  Edge toEdge){
        return Conditions.group(new FieldBetweenCondition(
                fieldNameProvider.getName(),"between",
                getValue(from),fromEdge,getValue(to),toEdge)
        );
    }

    /**
     * match
     * @param o value
     * @return builder
     */
    public GroupConditionBuilder match(Object o){
        return Conditions.group(new FieldCondition(fieldNameProvider.getName(),"match",getValue(o)));
    }


    private String getValue(Object o){
        return Converters.get(o.getClass()).convert(o);
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

    private static class FieldBetweenCondition implements Condition{
        String filedName;
        String compare;
        String from;
        String fromEdge;
        String to;
        String toEdge;

        public FieldBetweenCondition(String filedName, String compare, String from, Edge fromEdge, String to, Edge toEdge) {
            this.filedName = filedName;
            this.compare = compare;
            this.from = from;
            this.fromEdge = fromEdge==Edge.include?"[":"(";
            this.to = to;
            this.toEdge = toEdge==Edge.include?"]":")";
        }

        @Override
        public String toString() {
            return BuilderUtils.join(filedName,compare,fromEdge,from,",",to,toEdge);
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
