package nl.fontys.restaurantreservations.other.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
//
//    @Bean
//    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//
//        CorsConfiguration config = new CorsConfiguration();
//
//        // Frontends allowed to access the backend
//        config.setAllowedOrigins(List.of(
//                "http://localhost:5173",
//                "http://192.168.193.96:5174"
//        ));
//
//        // Allowed HTTP methods
//        config.setAllowedMethods(List.of(
//                "GET",
//                "POST",
//                "PUT",
//                "PATCH",
//                "DELETE"
//        ));
//
//        // Allowed request headers
//        config.setAllowedHeaders(List.of(
//                "Authorization",
//                "Content-Type"
//        ));
//
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//
//        source.registerCorsConfiguration("/apiv1/**", config);
//
//        return source;
//    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(
                        "http://localhost:5173",
                        "http://192.168.193.96:5174"
                );
            }
        };
    }
}
