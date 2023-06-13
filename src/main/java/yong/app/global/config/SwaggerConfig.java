package yong.app.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .contact(new Contact()
                        .name("yongcha")
                        .email("yongcha@gmail.com")
                        .url("https://localhost:9000"))
                .version("v1.0")
                .title("영차 서비스 API")
                .description("영차 서비스 API");
        return new OpenAPI()
                .info(info);
    }





}
