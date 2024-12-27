package com.forumhub.config;

import com.forumhub.security.JwtAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/forumhub/users").permitAll()
                .antMatchers("/forumhub/auth/login").permitAll()
                .antMatchers("/forumhub/courses").authenticated()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // Retorna 403 caso a autenticação falhe
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"" + authException.getMessage() + "\"}");
                });

        // Adiciona o filtro JWT antes do filtro de autenticação padrão
        http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
