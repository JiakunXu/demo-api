package com.example.demo.security.api.bo;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permission implements GrantedAuthority {

    private static final long serialVersionUID = 8320092931133753193L;

    private String            authority;

    public Permission() {

    }

    public Permission(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
