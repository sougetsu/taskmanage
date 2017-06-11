package com.sdmx.framework.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javolution.util.FastMap;

import com.sdmx.framework.vo.PaggingDatas;

/**
 * @author Yan Jingchao
 */

@SuppressWarnings("rawtypes")
public class PageUtil<T> {

    public static PaggingDatas getPaggingDatas(HttpServletRequest request, Long total) {
        String sortField = request.getParameter("sidx");
        String sortType = request.getParameter("sord");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        return new PaggingDatas(Integer.valueOf(page), Integer.valueOf(rows), sortField, sortType, total);
    }

    public static Map<String, Object> packPaggingDatas(PaggingDatas paggingDatas) {
        Map<String, Object> result = FastMap.newInstance();
        result.put("total", Math.ceil((double) paggingDatas.getTotal() / (double) paggingDatas.getRows()));
        result.put("page", paggingDatas.getPage());
        result.put("records", paggingDatas.getTotal());
        result.put("data", paggingDatas.getDatas());
        return result;
    }
}
