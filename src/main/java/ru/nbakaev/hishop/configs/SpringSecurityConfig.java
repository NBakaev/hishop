package ru.nbakaev.hishop.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import ru.nbakaev.hishop.auth.LocalAuthenticationProvider;
import ru.nbakaev.hishop.auth.UserAccountRoles;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LocalAuthenticationProvider localAuthenticationProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // allow all users including with failed auth to access static html, css, js files
        web.ignoring().antMatchers("/app/**");
        web.ignoring().antMatchers("/assets/**");
        web.ignoring().antMatchers("/bower_components/**");
        web.ignoring().antMatchers("/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().disable().rememberMe().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/actuator/**").hasRole(UserAccountRoles.ROLE_ADMIN.replace("ROLE_", ""))
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/api/v1/myaccount/**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .httpBasic().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.addHeader("WWW-Authenticate", "Application driven");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
            }
        })
                .and().csrf().disable();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(localAuthenticationProvider);
    }

}
