package by.it_academy.product.config;

import by.it_academy.product.web.controllers.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter filter) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) ->
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            )
                )
                .accessDeniedHandler(
                        (request, response, ex) ->
                                response.setStatus(
                                        HttpServletResponse.SC_FORBIDDEN
                                )
                )
                .and();
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/product/{uuid}/dt_update/{dt_update}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/product").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/product").permitAll()
                        .requestMatchers(HttpMethod.POST,"/recipe").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/recipe").permitAll()
                        .requestMatchers("/recipe/{uuid}/dt_update/{dt_update}").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}