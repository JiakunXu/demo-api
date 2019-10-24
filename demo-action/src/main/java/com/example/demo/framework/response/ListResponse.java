package com.example.demo.framework.response;

import com.example.demo.framework.constant.Constants;

import java.util.List;

/**
 * @author JiakunXu
 */
public class ListResponse<T> extends AbstractResponse {

    private int     total;

    private List<T> list;

    public ListResponse(int total, List<T> list) {
        this.setCode(Constants.SUCCESS_CODE);
        this.setTotal(total);
        this.setList(list);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
