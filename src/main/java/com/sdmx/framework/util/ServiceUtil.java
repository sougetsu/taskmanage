package com.sdmx.framework.util;

import java.util.Map;

import javolution.util.FastMap;

/**
 * User: Yan Jingchao
 * Date: 12-7-13
 * Time: 上午11:18
 */
public class ServiceUtil {

    private static final String FLAG = "flag";

    public static Map<String, Object> returnSuccess() {
        Map<String, Object> result = FastMap.newInstance();
        result.put(FLAG, Boolean.TRUE);
        return result;
    }

    public static Map<String, Object> returnSuccess(Map<String, Object> params) {
        params.putAll(returnSuccess());
        return params;
    }

    public static Map<String, Object> returnError() {
        Map<String, Object> result = FastMap.newInstance();
        result.put(FLAG, Boolean.FALSE);
        return result;
    }

    public static Map<String, Object> returnError(Map<String, Object> params) {
        params.putAll(returnError());
        return params;
    }

    public static boolean isSuccess(Map<String, Object> result) {
        return Boolean.valueOf(String.valueOf(result.get(FLAG)));
    }
}
