package ts.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class Cors implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Ustawia globalne CORS dla wszystkich endpointów
                .allowedOrigins("http://localhost:9000") // Pozwala na żądania z dowolnego źródła
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Pozwala na określone metody HTTP
                .allowedHeaders("*"); // Pozwala na określone nagłówki HTTP
    }
}