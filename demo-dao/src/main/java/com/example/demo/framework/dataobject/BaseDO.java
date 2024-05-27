package com.example.demo.framework.dataobject;

import com.example.demo.framework.query.BaseQuery;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Date;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class BaseDO extends BaseQuery {

    @Serial
    private static final long serialVersionUID = 8461456313695325022L;

    private Boolean           deleted;

    private String            creator;

    private String            modifier;

    private Date              createTime;

    private Date              updateTime;

}
