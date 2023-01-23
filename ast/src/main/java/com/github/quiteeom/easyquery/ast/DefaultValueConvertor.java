package com.github.quiteeom.easyquery.ast;


import com.github.quiteeom.easyquery.core.values.BoolValue;
import com.github.quiteeom.easyquery.core.values.LocalDateTimeValue;
import com.github.quiteeom.easyquery.core.values.NumberValue;
import com.github.quiteeom.easyquery.core.values.StringValue;
import com.github.quiteeom.easyquery.core.values.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author quitee
 * @date 2022/11/30
 */

public class DefaultValueConvertor {

    public Value convert(Token token) {
        switch (token.getType()){
            case BOOL_TRUE:
                return BoolValue.TRUE;
            case BOOL_FALSE:
                return BoolValue.FALSE;
            case STRING:
                String raw = token.getLiteral().getValue();
                return new StringValue(raw.substring(1,raw.length()-1));
            case TEXT:
                try {
                    LocalDateTime localDateTime = LocalDateTime.parse(token.getLiteral().getValue(), DateTimeFormatter.ISO_DATE_TIME);
                    return new LocalDateTimeValue(localDateTime);
                }catch (Exception e){
                    // ignore
                }
                try {
                    Long aLong = Long.parseLong(token.getLiteral().getValue());
                    return new NumberValue(aLong);
                }catch (Exception e){
                    // ignore
                }
                try {
                    Double aDouble = Double.parseDouble(token.getLiteral().getValue());
                    return new NumberValue(aDouble);
                }catch (Exception e){
                    // ignore
                }
            default:
                throw new RuntimeException("unknown type:"+token);
        }
    }


}
