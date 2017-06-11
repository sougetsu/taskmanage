package com.sdmx.framework.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author Yan Jingchao
 */
public class ObjectType {

    @SuppressWarnings("unchecked")
    public static boolean isEmpty(Object value) {
        if (value == null)
            return true;
        if (value instanceof String)
            return ((String) value).length() == 0;
        if (value instanceof Collection)
            return ((Collection<? extends Object>) value).size() == 0;
        if (value instanceof Map)
            return ((Map<? extends Object, ? extends Object>) value).size() == 0;
        if (value instanceof CharSequence)
            return ((CharSequence) value).length() == 0;
        if (value instanceof IsEmpty)
            return ((IsEmpty) value).isEmpty();
        if (value instanceof Boolean)
            return false;
        if (value instanceof Number)
            return false;
        if (value instanceof Character)
            return false;
        if (value instanceof java.util.Date)
            return false;
        return false;
    }
}
