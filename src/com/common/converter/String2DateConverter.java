package com.common.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LiChaoJie
 * @description
 * @date 2017-09-28 9:33
 * @modified By
 */
public class String2DateConverter implements Converter<String, Date> {

    public Date convert(String source) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
