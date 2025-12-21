package com.hcc.config;

import com.hcc.filters.JwtFilter;
import com.hcc.services.UserDetailServiceImpl;
import com.hcc.utils.CustomPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConf {

    private final UserDetailServiceImpl userDetailServiceImpl;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final JwtFilter jwtFilt;

    public SecurityConf(UserDetailServiceImpl userDetailServiceImpl,
                        CustomPasswordEncoder customPasswordEncoder,
                        JwtFilter jwtFilt) {
        this.userDetailServiceImpl = userDetailServiceImpl;
        this.customPasswordEncoder = customPasswordEncoder;
        this.jwtFilt = jwtFilt;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(
                        request -> new CorsConfiguration().applyPermitDefaultValues()
                ))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(
                        (request, response, exception) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage())
                ))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtFilt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailServiceImpl);
        provider.setPasswordEncoder(customPasswordEncoder.getPasswordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}