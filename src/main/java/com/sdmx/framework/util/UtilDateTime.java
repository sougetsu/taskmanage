package com.sdmx.framework.util;

import java.sql.Timestamp;

/**
 * @author Yan Jingchao
 */
public class UtilDateTime {

    public static Timestamp nowTimestamp() {

        return new Timestamp(System.currentTimeMillis());
    }
}
