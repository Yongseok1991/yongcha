package yong.app.global.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yong.app.domain.auth.RoleType;
import yong.app.domain.auth.YongRole;
import yong.app.domain.auth.YongUsersRole;
import yong.app.domain.user.UserForm;
import yong.app.domain.user.YongUser;
import yong.app.global.interceptor.LoggingInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    @Bean // IoC가 된다.
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/app-assets/**", "/assets/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        userController(registry);
    }

    // userController
    private void userController(ViewControllerRegistry registry) {
        registry.addViewController("/users").setViewName("user/index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("app/home/home");
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        // 매핑 추가 전략 설정 (커스텀)
        //modelMapper.addMappings(memberDTOtoMember());
//        modelMapper.addMappings(memberToMemberVO());

        // 매핑 전략 설정
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)  // 필드 접근 제한자 레벨을 Private으로 변경
                .setFieldMatchingEnabled(true);     // allow private fields to be matched -> default값 : disabled
        return modelMapper;
    }


//    public PropertyMap<YongUser, UserForm> memberToMemberVO() {
//        List<RoleType> roleTypes = new ArrayList<>();
//        PropertyMap<YongUser, UserForm> replyMapping = new PropertyMap<>() {
//            @Override
//            protected void configure() {    // 매핑 정보 입력
//                map().setEmail(source.getEmail());
//
//                // source -> get RoleTypes -> add to list
//                Set<YongUsersRole> yongRoles = source.getYongRoles();
//                for(YongUsersRole yongUsersRole : yongRoles){
//                    roleTypes.add(yongUsersRole.getYongRole().getRoleType());
//                }
//
//                map().setRoleType(roleTypes);
//            }
//        };
//        return replyMapping;
//    }

    /* redirect 필요 시
    @GetMapping("/")
    public String hello() {
        return "redirect:hello";
    }
    ---------------------------------->>
    registry.addRedirectViewController("/", "/hello");
    */
}
