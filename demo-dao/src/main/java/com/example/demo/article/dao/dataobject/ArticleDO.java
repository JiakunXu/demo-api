package com.example.demo.article.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigInteger;

@Getter
@Setter
@ToString
public class ArticleDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = -4068500118415403863L;

    private BigInteger        id;

    public ArticleDO() {
    }

    public ArticleDO(BigInteger id) {
        this.id = id;
    }

}
