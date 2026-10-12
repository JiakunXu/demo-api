package com.example.demo.security.authentication;

import com.example.demo.security.api.bo.Permission;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class AuthenticationTokenTest {
    @Test
    void principalOnlyTokenIsUnauthenticatedAndHasNoCredentials() {
        var principal = new Object(); var token = new AuthenticationToken(principal);
        assertThat(token.getPrincipal()).isSameAs(principal);
        assertThat(token.isAuthenticated()).isFalse();
        assertThat(token.getCredentials()).isNull();
        assertThat(token.getAuthorities()).isEmpty();
    }

    @Test
    void tokenWithGrantedAuthoritiesIsAuthenticated() {
        var token = new AuthenticationToken("user", List.of(new Permission("user:read")));
        assertThat(token.isAuthenticated()).isTrue(); assertThat(token.getPrincipal()).isEqualTo("user");
        assertThat(token.getAuthorities()).extracting(org.springframework.security.core.GrantedAuthority::getAuthority).containsExactly("user:read");
        token.setAuthenticated(false); assertThat(token.isAuthenticated()).isFalse();
    }
}
