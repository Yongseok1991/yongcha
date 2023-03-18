package yong.app.com.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class CustomPageableConfig {
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return p -> {
            p.setSizeParameterName("perPage");
            p.setOneIndexedParameters(true);
            p.setMaxPageSize(2000);
        };
    }
}
