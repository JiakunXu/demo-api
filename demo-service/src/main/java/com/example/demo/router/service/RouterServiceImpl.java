package com.example.demo.router.service;

import com.example.demo.router.api.RouterService;
import com.example.demo.router.api.bo.Router;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Service
public class RouterServiceImpl implements RouterService {

    @Override
    public List<Router> listRouters(BigInteger userId,
                                    Collection<? extends GrantedAuthority> authorities) {
        return null;
    }

}
