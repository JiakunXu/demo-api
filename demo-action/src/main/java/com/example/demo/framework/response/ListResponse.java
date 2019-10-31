package com.example.demo.framework.response;

import com.example.demo.framework.constant.Constants;

import java.util.List;

/**
 * @author JiakunXu
 */
public class ListResponse<T> extends AbstractResponse {

    private Integer total;

    private List<T> list;

    public ListResponse(int total, List<T> list) {
        this.setCode(Constants.SUCCESS);
        this.setTotal(total);
        this.setList(list);
    }

    public ListResponse(List<T> list) {
        this.setCode(Constants.SUCCESS);
        this.setList(list);
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
