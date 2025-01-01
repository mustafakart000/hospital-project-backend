package com.hospital.security.config;

import com.hospital.repository.UserRepository;
import com.hospital.security.JwtAuthenticationFilter;
import com.hospital.security.JwtUtil;
import com.hospital.model.Role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

// Uygulamanın güvenlik yapılandırmasını sağlayan sınıf
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Role bazlı yetkilendirme için eklendi

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter)
            throws Exception {
        // Güvenlik filtre zincirini yapılandırır
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/doctor/login", "/auth/admin/login", "/auth/register","http://localhost:3000/**")
                        
                        .permitAll()
                        .requestMatchers("/auth/doctor/register", "/auth/admin/register").hasRole(Role.ADMIN.name())

                        .requestMatchers("/doctor/update/**", "/doctor/diagnoses/**", "/doctor/diagnoses/create",
                                "/doctor/diagnoses/update/{id}",
                                "/doctor/diagnoses/delete/{id}", "/doctor/diagnoses/get/{id}",
                                "/doctor/diagnoses/patient/get/{patientId}")
                        .hasRole(Role.DOCTOR.name())

                        .requestMatchers("/patient/**", "/reservations/cancel/{id}", "/reservations/create")
                        .hasRole(Role.PATIENT.name())

                        .requestMatchers("/reservations/get/{id}").hasAnyRole(Role.PATIENT.name(), Role.DOCTOR.name())

                        .requestMatchers("/reservations/getall", "/reservations/update/{id}",
                                "/reservations/delete/{id}", "/reservations/get/doctor/{doctorId}")
                        .hasAnyRole(Role.ADMIN.name(), Role.DOCTOR.name(), Role.PATIENT.name())

                        .requestMatchers("/medical-record/**").hasAnyRole(Role.PATIENT.name(), Role.DOCTOR.name())

                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())

                        .requestMatchers("/doctor/**").hasAnyRole(Role.ADMIN.name(), Role.DOCTOR.name())

                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        // JWT doğrulama filtresini oluşturur
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        // Kullanıcı detaylarını yükleyen bir servis sağlar
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Şifreleme için bir PasswordEncoder sağlar

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Kimlik doğrulama yöneticisini sağlar
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // CORS yapılandırmasını sağlar
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // source.registerCorsConfiguration("/", configuration);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}