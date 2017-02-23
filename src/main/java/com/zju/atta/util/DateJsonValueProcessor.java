package com.zju.atta.util;

import net.sf.json.JsonConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by honeycc on 16-11-24.
 */
public class DateJsonValueProcessor {
    private String format = "yyyy-MM-dd";

    public DateJsonValueProcessor() {
    }

    public DateJsonValueProcessor(String format) {
        this.format = format;
    }

    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        String[] obj = {};
        if(value!=null)
        {
            if (value instanceof Date[]) {
                SimpleDateFormat sf = new SimpleDateFormat(format);
                Date[] dates = (Date[]) value;
                obj = new String[dates.length];
                for (int i = 0; i < dates.length; i++) {
                    obj[i] = sf.format(dates[i]);
                }
            }
        }
        else
            return "";
        return obj;
    }

    public Object processObjectValue(String key, Object value,
                                     JsonConfig jsonConfig) {
        if(value!=null)
        {
            if (value instanceof Date) {
                String str = new SimpleDateFormat(format).format((Date) value);
                return str;
            }
        }
        else
            return "";
        return value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
