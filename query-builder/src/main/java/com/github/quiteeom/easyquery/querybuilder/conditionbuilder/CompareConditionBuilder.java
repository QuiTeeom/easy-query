package com.github.quiteeom.easyquery.querybuilder.conditionbuilder;

import com.github.quiteeom.easyquery.core.condition.CompareOpt;
import com.github.quiteeom.easyquery.core.condition.CompareOpts;
import com.github.quiteeom.easyquery.core.condition.Condition;
import com.github.quiteeom.easyquery.core.condition.LogicTypes;
import com.github.quiteeom.easyquery.core.condition.SimpleCompareCondition;
import com.github.quiteeom.easyquery.core.values.RangeValue;
import com.github.quiteeom.easyquery.core.values.Value;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author quitee
 * @date 2022/11/30
 */

public class CompareConditionBuilder {

    private CompareValueConvertor valueConvertor;
    private CompareTargetProvider targetProvider;

    public CompareConditionBuilder(String target) {
        this(new DefaultValueConvertor(),new DefaultCompareTargetProvider(target));
    }

    public CompareConditionBuilder(CompareTargetProvider targetProvider) {
        this(new DefaultValueConvertor(),targetProvider);
    }

    public CompareConditionBuilder(CompareValueConvertor valueConvertor,String target) {
        this(valueConvertor,new DefaultCompareTargetProvider(target));
    }

    public CompareConditionBuilder(CompareValueConvertor valueConvertor,CompareTargetProvider targetProvider) {
        this.targetProvider = targetProvider;
        this.valueConvertor = valueConvertor;
    }



    /**
     * =
     * @param o value
     * @return builder
     */
    public LogicConditionBuilder eq(Object o){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider, CompareOpts.EQ,valueConvertor.convert(o)));
    }

    /**
     * !=
     * @param o value
     * @return builder
     */
    public LogicConditionBuilder notEq(Object o){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.NOT_EQ,valueConvertor.convert(o)));
    }

    /**
     * >
     * @param o value
     * @return builder
     */
    public LogicConditionBuilder gt(Object o){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.GT,valueConvertor.convert(o)));
    }

    /**
     * >=
     * @param o value
     * @return builder
     */
    public LogicConditionBuilder gtEq(Object o){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.GT_EQ,valueConvertor.convert(o)));
    }

    /**
     * <
     * @param o value
     * @return builder
     */
    public LogicConditionBuilder lt(Object o){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.LT,valueConvertor.convert(o)));
    }

    /**
     * <=
     * @param o value
     * @return builder
     */
    public LogicConditionBuilder ltOrEq(Object o){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.LT_EQ,valueConvertor.convert(o)));
    }

    /**
     * in
     * @param collections values
     * @return builder
     */
    public LogicConditionBuilder in(Collection<?> collections){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.IN,valueConvertor.convert(collections)));
    }

    /**
     * not in
     * @param objects values
     * @return builder
     */
    public LogicConditionBuilder notIn(Object... objects){
        return in(Arrays.asList(objects));
    }

    /**
     * not in
     * @param collections values
     * @return builder
     */
    public LogicConditionBuilder notIn(Collection<?> collections){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.NOT_IN,valueConvertor.convert(collections)));
    }

    /**
     * in
     * @param objects values
     * @return builder
     */
    public LogicConditionBuilder in(Object... objects){
        return in(Arrays.asList(objects));
    }

    /**
     * contains
     * @param o value
     * @return builder
     */
    public LogicConditionBuilder contains(String o){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.CONTAINS,valueConvertor.convert(o)));
    }

    /**
     * startWith
     * @param o value
     * @return builder
     */
    public LogicConditionBuilder startWith(String o){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.START_WITH,valueConvertor.convert(o)));
    }


    /**
     * endWith
     * @param o value
     * @return builder
     */
    public LogicConditionBuilder endWith(Object o){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.END_WITH,valueConvertor.convert(o)));
    }

    /**
     * between
     * @param from from
     * @param fromBoundary fromEdge, [ or (
     * @param to to
     * @param  fromBoundary toEdge, ] or )
     * @return builder
     */
    public LogicConditionBuilder between(RangeValue.Boundary fromBoundary, Object from, Object to, RangeValue.Boundary toBoundary){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(
                targetProvider,
                CompareOpts.BETWEEN,
                new RangeValue(
                        fromBoundary,
                        valueConvertor.convert(from),
                        valueConvertor.convert(to),
                        toBoundary
                )
        ));
    }

    /**
     * match
     * @param o value
     * @return builder
     */
    public LogicConditionBuilder match(Object o){
        return new LogicConditionBuilder(LogicTypes.AND).withConditions(new SimpleCompareConditionBuilder(targetProvider,CompareOpts.MATCH,valueConvertor.convert(o)));
    }


    private static class DefaultCompareTargetProvider implements CompareTargetProvider{
        String target;

        public DefaultCompareTargetProvider(String target) {
            this.target = target;
        }

        @Override
        public String getTarget() {
            return target;
        }
    }

    private static class SimpleCompareConditionBuilder implements ConditionBuilder {
        CompareTargetProvider targetProvider;
        CompareOpt opt;
        Value value;

        public SimpleCompareConditionBuilder(CompareTargetProvider targetProvider, CompareOpt opt, Value value) {
            this.targetProvider = targetProvider;
            this.opt = opt;
            this.value = value;
        }

        @Override
        public Condition build() {
            return new SimpleCompareCondition(targetProvider.getTarget(),opt,value);
        }
    }
}
