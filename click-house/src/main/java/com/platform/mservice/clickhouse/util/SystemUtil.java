package com.platform.mservice.clickhouse.util;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/3/29 下午2:17
 */
public class SystemUtil {

    public static String loadProp(String key, String def) {
        String property = System.getProperty(key);
        if (property != null)
            return property;
        String env = System.getenv(key);
        if (env != null)
            return env;
        return def;
    }
}
