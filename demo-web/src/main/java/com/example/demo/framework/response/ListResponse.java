package com.example.demo.framework.response;

import com.example.demo.framework.constant.HttpStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class ListResponse<T> extends AbstractResponse {

    private static final long serialVersionUID = -5567902033588518919L;

    private Integer           total;

    private List<T>           rows;

    public ListResponse(int total, List<T> rows) {
        this.setCode(HttpStatus.SUCCESS);
        this.setTotal(total);
        this.setRows(rows);
    }

    public ListResponse(List<T> rows) {
        this.setCode(HttpStatus.SUCCESS);
        this.setRows(rows);
    }

}
