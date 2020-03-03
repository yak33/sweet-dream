package com.junya.util.result;

import java.io.Serializable;
import java.util.List;

/**
 * 用于返回相关信息的对象中需要分页的data
 *
 * @Author ZHANGCHAO
 * @Date 2020/2/26 12:17
 * @retrun
 **/
@SuppressWarnings("unused")
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageSize = 0; //每页条数
    private int totalPage = 0; //总页数
    private int pageNumber = 0; //页码
    private int totalRow = 0; //总记录数
    private List<T> list; //数据

    public ResponseData() {
    }

    public ResponseData(int pageNumber, int totalPage, int totalRow, List<T> list) {
        this.pageNumber = pageNumber;
        this.totalPage = totalPage;
        this.totalRow = totalRow;
        this.list = list;
    }

    public ResponseData(int pageSize, int totalPage, int pageNumber, int totalRow, List<T> list) {
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.pageNumber = pageNumber;
        this.totalRow = totalRow;
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public ResponseData setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public ResponseData setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public ResponseData setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public ResponseData setTotalRow(int totalRow) {
        this.totalRow = totalRow;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public ResponseData setList(List<T> list) {
        this.list = list;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "\"pageSize\":" +
                pageSize +
                ",\"totalPage\":" +
                totalPage +
                ",\"pageNumber\":" +
                pageNumber +
                ",\"totalRow\":" +
                totalRow +
                ",\"list\":" +
                list +
                '}';
    }
}
