package com.goodjob.crm.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.goodjob.crm.security.AppUserDetailsService;
import com.goodjob.crm.security.Http401UnauthorizedEntryPoint;
import com.goodjob.crm.security.JWTAuthenticationConverter;
import com.goodjob.crm.security.JWTAuthenticationFilter;
import com.goodjob.crm.security.JWTAuthenticationProvider;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        List<AuthenticationProvider> providers = new ArrayList<>();
        DaoAuthenticationProvider tokenProvider = new DaoAuthenticationProvider();
        tokenProvider.setUserDetailsService(userDetailsService());
        tokenProvider.setPasswordEncoder(passwordEncoder());

        providers.add(tokenProvider);
        providers.add(jwtAuthenticationProvider());
        return new ProviderManager(providers);
    }

    @Bean
    public AuthenticationConverter authenticationConverterBean() throws Exception {
        return new JWTAuthenticationConverter();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    @Bean
    public JWTAuthenticationProvider jwtAuthenticationProvider() {
        return new JWTAuthenticationProvider();
    }

    @Bean
    public JWTAuthenticationFilter authenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new Http401UnauthorizedEntryPoint();
    }

}
