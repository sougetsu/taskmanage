package com.sdmx.framework.vo;

import java.util.List;

/**
 * @author Yan Jingchao
 */
public class PaggingDatas<T> {

    private int page;
    private int rows;
    private String sortField;
    private String sortType;
    private Long total;
    private List<T> datas;

    public PaggingDatas(int page, int rows, String sortField, String sortType, Long total) {
        this.page = page;
        this.rows = rows;
        this.sortField = sortField;
        this.total = total;
        this.sortType = sortType;
    }

    public int getCurrentPage() {
        return page - 1;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }


    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
