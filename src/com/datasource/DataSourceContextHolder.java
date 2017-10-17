package com.datasource;

/**
 * @author LiChaoJie
 * @description
 * @date 2017-09-27 14:35
 * @modified By
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    private DataSourceContextHolder() {
    }

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return  contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
