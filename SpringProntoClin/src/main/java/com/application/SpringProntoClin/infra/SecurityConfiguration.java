package com.application.SpringProntoClin.infra;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "auth/register/paciente").permitAll()
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/paciente/**").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.PUT, "/paciente/**").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/paciente/**").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/adm/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/adm/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "auth/register/adm").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "auth/register/prosaude").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/profSaude/profissionais").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/profSaude/AllProfissionais").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/profSaude/StatusProfissional").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/consulta/paciente/**").hasRole("PACIENTE")
                        //.requestMatchers(HttpMethod.GET, "/consulta/paciente/**").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.POST, "/consulta/**").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.PUT, "/consulta/**").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/profSaude/me").hasRole("PROFSAUDE")
                        .requestMatchers(HttpMethod.PUT, "/profSaude/atualiza").hasRole("PROFSAUDE")
                        .requestMatchers(HttpMethod.POST, "/consulta/agendaprofissional").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/consulta/profissional/consultas").hasRole("PROFSAUDE")
                        .requestMatchers(HttpMethod.PUT, "/prontuario/atualizarProntuario").hasRole("PROFSAUDE")
                        .requestMatchers(HttpMethod.POST, "/prontuario/adicionarProntuario").hasRole("PROFSAUDE")
                        .requestMatchers(HttpMethod.GET, "/prontuario/prontuarioPaciente").hasRole("PROFSAUDE")
                        .requestMatchers(HttpMethod.GET, "/prontuario/meuprontuario").hasRole("PACIENTE")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
