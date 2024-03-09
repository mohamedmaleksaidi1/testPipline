package com.centranord.Config;



import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;


import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {


    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",

            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/api/v1/users/**",


            };


    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL).permitAll()
//*************************************************************GestionEmail & permission***************************
                                .requestMatchers(POST,"/api/v1/Email/**").hasAnyAuthority("EmailAJOUTER")
                                .requestMatchers(GET,"/api/v1/Email/**").hasAnyAuthority("EmailLIRE")
                                .requestMatchers(PATCH,"/api/v1/Email/**").hasAnyAuthority("EmailMODIFIER")
                                .requestMatchers(DELETE,"/api/v1/Email/**").hasAnyAuthority("EmailSUPPRIMER")
//**********************************************************Gestion Role & Permission*********************
                                .requestMatchers(POST,"/api/v1/roles/**").hasAnyAuthority("RoleAJOUTER")
                                .requestMatchers(GET,"/api/v1/roles/**").hasAnyAuthority("RoleLIRE")
                                .requestMatchers(PATCH,"/api/v1/roles/**").hasAnyAuthority("RoleMODIFIER")
                                .requestMatchers(DELETE,"/api/v1/roles/**").hasAnyAuthority("RoleSUPPRIMER")
//**********************************************************Gestion user & Permission*********************
                                .requestMatchers(POST,"/api/v1/users/**").hasAnyAuthority("userAJOUTER")
                                .requestMatchers(GET,"/api/v1/users/**").hasAnyAuthority("usersLIRE")
                                .requestMatchers(PATCH,"/api/v1/users/**").hasAnyAuthority("userMODIFIER")
                                .requestMatchers(DELETE,"/api/v1/users/**").hasAnyAuthority("userSUPPRIMER")





                                .anyRequest()
                                .authenticated()

                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
          .exceptionHandling(exceptionHandling ->
                exceptionHandling.accessDeniedHandler(accessDeniedHandler())
        );
        http
                .addFilterAfter(new RoleLoggingFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public HttpStatusEntryPoint httpStatusEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }

    public class CustomAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Vous n'êtes pas autorisé à accéder à cette ressource");
        }
    }

}
