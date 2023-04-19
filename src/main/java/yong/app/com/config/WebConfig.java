package yong.app.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yong.app.com.interceptor.LoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor())
                .addPathPatterns("/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        userController(registry);
    }

    // userController
    private void userController(ViewControllerRegistry registry) {
        registry.addViewController("/users").setViewName("user/index");
        registry.addViewController("/login").setViewName("login");
    }

    /* redirect 필요 시
    @GetMapping("/")
    public String hello() {
        return "redirect:hello";
    }
    ---------------------------------->>
    registry.addRedirectViewController("/", "/hello");
    */
}
