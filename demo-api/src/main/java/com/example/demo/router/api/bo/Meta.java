package com.example.demo.router.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Meta implements Serializable {

    @Serial
    private static final long serialVersionUID = -4223565486924864690L;

    private boolean           noCache;

    private String            title;

    private String            icon;

    private boolean           breadcrumb;

    private String            activeMenu;

    private String            link;

}
