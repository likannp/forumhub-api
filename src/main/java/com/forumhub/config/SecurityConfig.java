package com.forumhub.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Desabilitar CSRF para testes
                .authorizeRequests()
                .antMatchers("/forumhub/users").permitAll()  // Permitir acesso sem autenticação para esse endpoint
                .anyRequest().authenticated()  // Requer autenticação para os outros endpoints
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) ->
                        response.sendError(403, "Acesso proibido")
                ); // Manipulador de erro para respostas 403
    }
}
