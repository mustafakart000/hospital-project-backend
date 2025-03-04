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
                                                .requestMatchers(
                                                                "/api/auth/login", "/api/auth/doctor/login",
                                                                "/api/auth/admin/login", "/api/auth/register",
                                                                "/api/v1/auth/test",
                                                                "http://localhost:3000/**",
                                                                "/api/auth/public-key")
                                                .permitAll()
                                                .requestMatchers("/api/auth/doctor/register",
                                                                "/api/auth/admin/register")
                                                .hasRole(Role.ADMIN.name())


                                                .requestMatchers("/api/lab-requests/patient/{patientId}/pdfs","/api/lab-requests/patient/{patientId}/pdfs/{pdfId}")
                                                .hasAnyRole(Role.DOCTOR.name(), Role.PATIENT.name(),Role.ADMIN.name(),Role.TECHNICIAN.name())
                                                .requestMatchers("/api/technician/lab-requests/all").hasAnyRole(Role.DOCTOR.name(),Role.ADMIN.name(),Role.TECHNICIAN.name())
                                                .requestMatchers("/api/technician/**", "/api/technicians/**", "/api/technicians/imaging-requests/**", "/api/technicians/lab-requests/**")
                                                .hasAnyRole(Role.DOCTOR.name(),Role.ADMIN.name(),Role.TECHNICIAN.name(),Role.PATIENT.name())
                                                .requestMatchers("/api/imaging-requests/create").hasAnyRole(Role.PATIENT.name(),Role.DOCTOR.name())
                                                .requestMatchers("/api/imaging-requests/patient/{patientId}","/api/imaging-requests/patient/{patientId}/data",
                                                               "/api/imaging-requests/patient/{patientId}/images/{imageId}","/api/imaging-requests/patient/{patientId}/images/{imageId}/raw"
                                                               ,"/api/imaging-requests/patient/{patientId}/images/{imageId}/data","/api/imaging-requests/patient/{patientId}/images/{imageId}/complete")
                                                .hasAnyRole(Role.PATIENT.name(),Role.DOCTOR.name(),Role.ADMIN.name(),Role.TECHNICIAN.name())
                                                .requestMatchers("/api/imaging-requests/patient/{patientId}/images/{imageId}").hasAnyRole(Role.PATIENT.name(),Role.DOCTOR.name())
                                                .requestMatchers("/api/auth/me", "/api/auth/allspecialties")
                                                .hasAnyRole(Role.ADMIN.name(), Role.DOCTOR.name(), Role.PATIENT.name(),Role.TECHNICIAN.name())
                                                .requestMatchers("/api/doctor/diagnoses/create").hasRole(Role.DOCTOR.name())
                                                .requestMatchers("/api/patient/get/{id}", "/api/patient/update/{id}")
                                                .hasAnyRole(Role.DOCTOR.name(), Role.PATIENT.name(),Role.ADMIN.name(),Role.TECHNICIAN.name())
                                                .requestMatchers("/api/medicine/get/{id}", "/api/medicine/createAll",
                                                                "/api/medicine/getAllByDoctorSpeciality/**").hasAnyRole(Role.DOCTOR.name(),Role.ADMIN.name())

                                                .requestMatchers("/lab-requests/patient/{patientId}/pdfs")
                                                .hasAnyRole(Role.DOCTOR.name(), Role.PATIENT.name(), Role.ADMIN.name(),
                                                                Role.TECHNICIAN.name())
                                                .requestMatchers("/technician/lab-requests/all")
                                                .hasAnyRole(Role.DOCTOR.name(), Role.ADMIN.name(),
                                                                Role.TECHNICIAN.name())
                                                .requestMatchers("/technician/**", "/technicians/**",
                                                                "/technicians/imaging-requests/**",
                                                                "/technicians/lab-requests/**")
                                                .hasAnyRole(Role.DOCTOR.name(), Role.ADMIN.name(),
                                                                Role.TECHNICIAN.name(), Role.PATIENT.name())
                                                .requestMatchers("/imaging-requests/create")
                                                .hasAnyRole(Role.PATIENT.name(), Role.DOCTOR.name())
                                                .requestMatchers("/imaging-requests/patient/{patientId}",
                                                                "/imaging-requests/patient/{patientId}/data",
                                                                "/imaging-requests/patient/{patientId}/images/{imageId}/data")
                                                .hasAnyRole(Role.PATIENT.name(), Role.DOCTOR.name(), Role.ADMIN.name(),
                                                                Role.TECHNICIAN.name())
                                                .requestMatchers("/auth/me", "/auth/allspecialties")
                                                .hasAnyRole(Role.ADMIN.name(), Role.DOCTOR.name(), Role.PATIENT.name(),
                                                                Role.TECHNICIAN.name())
                                                .requestMatchers("/doctor/diagnoses/create").hasRole(Role.DOCTOR.name())
                                                .requestMatchers("/patient/get/{id}", "/patient/update/{id}")
                                                .hasAnyRole(Role.DOCTOR.name(), Role.PATIENT.name(), Role.ADMIN.name(),
                                                                Role.TECHNICIAN.name())
                                                .requestMatchers("/medicine/get/{id}", "/medicine/createAll",
                                                                "/medicine/getAllByDoctorSpeciality/**")

                                                .hasAnyRole(Role.DOCTOR.name(), Role.ADMIN.name())
                                                .requestMatchers("/api/technician/**")
                                                .hasAnyRole(Role.DOCTOR.name(), Role.ADMIN.name(),
                                                                Role.TECHNICIAN.name())
                                                .requestMatchers("/api/imaging-requests/create")
                                                .hasAnyRole(Role.PATIENT.name(), Role.DOCTOR.name())
                                                .requestMatchers("/api/auth/me", "/api/auth/allspecialties")
                                                .hasAnyRole(Role.ADMIN.name(), Role.DOCTOR.name(), Role.PATIENT.name())
                                                .requestMatchers("/api/doctor/diagnoses/create")
                                                .hasRole(Role.DOCTOR.name())
                                                .requestMatchers("/api/patient/get/{id}", "/api/patient/update/{id}")
                                                .hasAnyRole(Role.DOCTOR.name(), Role.PATIENT.name())
                                                .requestMatchers("/api/medicine/get/{id}", "/api/medicine/createAll",
                                                                "/api/medicine/getAllByDoctorSpeciality/**")

                                                .hasAnyRole(Role.DOCTOR.name(), Role.ADMIN.name())
                                                .requestMatchers("/api/prescription/create").hasRole(Role.DOCTOR.name())
                                                .requestMatchers("/api/prescription/get/{id}")
                                                .hasRole(Role.DOCTOR.name())
                                                .requestMatchers("/api/prescription/get/patient")
                                                .hasRole(Role.PATIENT.name())
                                                .requestMatchers("/api/prescription/get/patient/{patientId}")
                                                .hasRole(Role.DOCTOR.name())
                                                .requestMatchers("/api/prescription/**").hasRole(Role.DOCTOR.name())
                                                .requestMatchers("/api/reservations/create")
                                                .hasRole(Role.PATIENT.name())
                                                .requestMatchers("/api/reservations/get/{id}",
                                                                "/api/reservations/get/doctor/{doctorId}",
                                                                "/api/reservations/get/patient/{patientId}",
                                                                "/api/reservations/delete/{id}")
                                                .hasAnyRole(Role.DOCTOR.name(), Role.PATIENT.name())
                                                .requestMatchers("/api/reservations/getall").hasRole(Role.DOCTOR.name())

                                                .requestMatchers("/api/medical-record/create",
                                                                "/api/medical-record/get/{id}",
                                                                "/api/medical-record/patient/{patientId}")
                                                .hasAnyRole(Role.PATIENT.name(), Role.DOCTOR.name())
                                                .requestMatchers("/api/medical-record/create/appointment")
                                                .hasRole(Role.DOCTOR.name())

                                                .requestMatchers("/api/admin/doctor/all")
                                                .hasRole(Role.ADMIN.name())

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
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList(
                        "http://37.148.209.189:8080",
                        "http://37.148.209.189",
                        "http://localhost:8080",
                        "http://localhost:3000",
                        "http://healwell.online"
                ));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Access-Control-Allow-Origin"));
                configuration.setExposedHeaders(Arrays.asList("Authorization"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

}
