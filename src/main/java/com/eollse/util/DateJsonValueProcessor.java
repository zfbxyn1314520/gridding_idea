package com.eollse.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * 转换Date类型的JSON工具类
 * @author Administrator
 *
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

    private String formate;

    public DateJsonValueProcessor() {
        //默认日期转换格式
        this.formate = "yyyy-MM-dd kk:mm:ss";
    }


    public DateJsonValueProcessor(String formate) {
        //按用户传入的格式字符串转换日期
        this.formate = formate;
    }

    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        String[] obj = {};
        if (value instanceof Date[]) {
            SimpleDateFormat sf = new SimpleDateFormat(formate);
            Date[] dates = (Date[]) value;
            obj = new String[dates.length];
            for (int i = 0; i < dates.length; i++) {
                obj[i] = sf.format(dates[i]);
            }
        }
        return obj;
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        if (value instanceof Date ) {
            String str = new SimpleDateFormat(this.formate).format((Date) value);
            return str;
        }
        return null;
    }

    public String getFormate() {
        return formate;
    }

    public void setFormate(String formate) {
        this.formate = formate;
    }


}
