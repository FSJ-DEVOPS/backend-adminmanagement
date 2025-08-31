package com.kce.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow specific origins
        configuration.addAllowedOrigin("http://localhost:5173");     // Vite dev server
        configuration.addAllowedOrigin("http://localhost:3000");     // React dev server
        configuration.addAllowedOrigin("https://shopverse-theta.vercel.app"); // Production domain
        configuration.addAllowedOriginPattern("https://*.vercel.app"); // All Vercel preview deployments
        
        // Allow all common HTTP methods
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("OPTIONS");
        
        // Allow all headers
        configuration.addAllowedHeader("*");
        
        // Allow credentials (for authentication)
        configuration.setAllowCredentials(true);
        
        // Apply to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
