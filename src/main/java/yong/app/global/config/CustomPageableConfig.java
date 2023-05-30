package yong.app.global.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;

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


//    public class xxxx implement 인터페이스

    // TODO sort할 때 사용
//    @Bean
//    public SortHandlerMethodArgumentResolverCustomizer sort() {
//        return null;
//    }
}
