package com.example.demo.router.api;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.example.demo.router.api.bo.Router;

public interface RouterService {

    List<Router> listRouters(BigInteger userId, Collection<? extends GrantedAuthority> authorities);

}
