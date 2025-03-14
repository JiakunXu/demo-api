package com.example.demo.framework.config;

import com.example.demo.framework.filter.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;

/**
 * @author JiakunXu
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private AuthenticationEntryPoint  authenticationEntryPoint;

    @Autowired
    private CorsFilter                corsFilter;

    @Autowired
    private LogoutSuccessHandler      logoutSuccessHandler;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers(headers -> {
            headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable);
            headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
        });

        httpSecurity.sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(authorizeHttpRequests -> {
            authorizeHttpRequests
                .requestMatchers("/alipay/notify", "/bytedance/notify", "/captcha/get",
                    "/dingtalk/notify", "/login", "/weixin/notify", "/ws", "/wxpay/notify")
                .permitAll();
            authorizeHttpRequests.anyRequest().authenticated();
        });

        httpSecurity.exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint(authenticationEntryPoint));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.logout(
            logout -> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler));

        httpSecurity.addFilterBefore(tokenAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(corsFilter, TokenAuthenticationFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);

        return httpSecurity.build();
    }

}
