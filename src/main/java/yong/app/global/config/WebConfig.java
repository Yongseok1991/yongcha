package yong.app.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yong.app.global.interceptor.LoggingInterceptor;


/**
* @fileName WebConfig
* @author yongseok
* @version 1.0.0
* @date 2023-04-19
* @summary intercepter 등록 및 url 및 viewName을 등록하기 위한 페이지다.
* 메뉴에 대한 인터셉터를 추가 구현해야한다. 차후에 메뉴와 권한 별로 추가 개발할 예정이다.
**/
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
