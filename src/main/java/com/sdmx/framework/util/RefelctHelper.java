package com.sdmx.framework.util;

/**
 * 反射的工具类
 *
 * @author Yan Jingchao
 */
public class RefelctHelper {

    /**
     * 标准bean根据方法名获取字段名
     *
     * @param methodName 方法名
     * @return 字段名
     */
    public static String getFieldName(String methodName) {
        return methodName.substring(3, 4).toLowerCase()
                + methodName.substring(4);
    }

    public static String getMethodName(String fieldName) {
        return "get" + StringUtils.firstLetterToUpper(fieldName);
    }
}
