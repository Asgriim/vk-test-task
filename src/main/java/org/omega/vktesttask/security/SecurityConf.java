package org.omega.vktesttask.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConf {


    private final AuthFilter authFilter;

    //todo delete headers and cors
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.headers(h -> h.frameOptions(f -> f.sameOrigin()))
                .cors(cors -> cors.disable())
                .csrf(cs -> cs.disable())
                .addFilterBefore(authFilter, AuthorizationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"/api/albums").hasAuthority("ALBUM_VIEWER")
                        .requestMatchers("/api/auth/*").permitAll()
                );


        return http.build();
    }




}