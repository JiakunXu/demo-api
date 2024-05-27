package com.example.demo.security.api.bo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;

@Getter
@Setter
public class Permission implements GrantedAuthority {

    @Serial
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
