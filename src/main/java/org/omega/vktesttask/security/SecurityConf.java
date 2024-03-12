package org.omega.vktesttask.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConf {

    private final AuthFilter authFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                //disabled for simplify things
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(authFilter, AuthorizationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/*").permitAll())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"/api/posts/**").hasAuthority("POST_VIEWER")
                        .requestMatchers(HttpMethod.PUT,"/api/posts/**").hasAuthority("POST_EDITOR")
                        .requestMatchers(HttpMethod.POST,"/api/posts/**").hasAuthority("POST_CREATOR")
                        .requestMatchers(HttpMethod.DELETE,"/api/posts/**").hasAuthority("POST_DELETER")
                        .requestMatchers(HttpMethod.GET,"/api/users/**").hasAuthority("USER_VIEWER")
                        .requestMatchers(HttpMethod.PUT,"/api/users/**").hasAuthority("USER_EDITOR")
                        .requestMatchers(HttpMethod.POST,"/api/users/**").hasAuthority("USER_CREATOR")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/**").hasAuthority("USER_DELETER")
                        .requestMatchers(HttpMethod.GET,"/api/albums/**").hasAuthority("ALBUM_VIEWER")
                        .requestMatchers(HttpMethod.PUT,"/api/albums/**").hasAuthority("ALBUM_EDITOR")
                        .requestMatchers(HttpMethod.POST,"/api/albums/**").hasAuthority("ALBUM_CREATOR")
                        .requestMatchers(HttpMethod.DELETE,"/api/albums/**").hasAuthority("ALBUM_DELETER")
                        .requestMatchers("/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                );


        return http.build();
    }




}