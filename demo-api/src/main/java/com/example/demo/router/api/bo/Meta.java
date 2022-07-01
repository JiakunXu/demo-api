package com.example.demo.router.api.bo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta implements Serializable {

    private static final long serialVersionUID = -4223565486924864690L;

    private boolean           noCache;

    private String            title;

    private String            icon;

    private boolean           breadcrumb;

    private String            activeMenu;

    private String            link;

}
