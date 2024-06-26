package com.example.demo.router.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Router implements Serializable {

    @Serial
    private static final long serialVersionUID = 6817001402729000213L;

    private boolean           hidden;

    private boolean           alwaysShow;

    private String            redirect;

    private String            path;

    private String            name;

    private String            component;

    private String            query;

    private String[]          roles;

    private String[]          permissions;

    private Meta              meta;

    private List<Router>      children;

}
