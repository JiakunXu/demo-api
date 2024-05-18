package com.example.demo.security.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.security.api.TokenService;
import com.example.demo.security.api.VersionService;
import com.example.demo.security.api.bo.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("com.example.demo.security.service.tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisService<String, LoginUser> redisService;

    @Autowired
    private UserDetailsService              userDetailsService;

    @Autowired
    private VersionService                  versionService;

    @Value("${secret.key}")
    private String                          secretKey;

    private final UserDetailsChecker        preAuthenticationChecks = new DefaultPreAuthenticationChecks();

    @Override
    public LoginUser getUser(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        LoginUser user = redisService.get(getKey(token));

        if (user == null) {
            return null;
        }

        if (!versionService.validate(user)) {
            user = (LoginUser) userDetailsService.loadUserByUsername(user.getUsername());

            if (user == null) {
                remove(token);
                return null;
            }

            try {
                preAuthenticationChecks.check(user);
            } catch (Exception e) {
                remove(token);
                return null;
            }

            redisService.set(getKey(token), user);

            versionService.set(user);
        }

        return user;
    }

    @Override
    public String setUser(LoginUser user) {
        String token = UUID.randomUUID().toString();

        redisService.add(token, user);

        versionService.set(user);

        Map<String, String> map = new HashMap<>();
        map.put(Claims.ID, token);

        return Jwts.builder().claims(map).signWith(getSecretKey(), Jwts.SIG.HS512).compact();
    }

    @Override
    public void refresh(String token) {
        if (StringUtils.isBlank(token)) {
            return;
        }

        redisService.expire(getKey(token));
    }

    @Override
    public void remove(String token) {
        if (StringUtils.isBlank(token)) {
            return;
        }

        redisService.remove(getKey(token));
    }

    private String getKey(String token) {
        Claims claims = Jwts.parser().verifyWith(getSecretKey()).build()
            .parseSignedClaims(StringUtils.removeStart(token, TOKEN_PREFIX)).getPayload();

        return claims.getId();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private static class DefaultPreAuthenticationChecks implements UserDetailsChecker {

        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                throw new LockedException("账号已锁定");
            }
            if (!user.isEnabled()) {
                throw new DisabledException("账号不可用");
            }
            if (!user.isAccountNonExpired()) {
                throw new AccountExpiredException("账号已过期");
            }
        }

    }

}
