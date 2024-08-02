package com.example.demo.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;

/**
 * @author JiakunXu
 */
public class AuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = -8610078825675278550L;

    private final Object      principal;

    public AuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false);
    }

    public AuthenticationToken(Object principal,
                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}
