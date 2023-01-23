package com.github.quiteeom.easyquery.core.values;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author quitee
 * @date 2022/12/18
 */

public class LocalDateTimeValue extends BaseValue<LocalDateTime>{

    public LocalDateTimeValue(LocalDateTime raw) {
        super(raw);
    }

    public LocalDateTimeValue(Date date) {
        super(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public String toQueryString() {
        return raw.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String type() {
        return "datetime";
    }
}
