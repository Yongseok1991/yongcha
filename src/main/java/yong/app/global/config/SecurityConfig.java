package yong.app.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import yong.app.global.auth.PrincipalOauth2UserService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable();
        http
            .authorizeRequests(authorize ->
                authorize
                        .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html").permitAll()
                    .mvcMatchers("/user/**").authenticated()
                    .mvcMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                    .anyRequest().permitAll()
            )
            .formLogin(formLoginConfigurer ->
                formLoginConfigurer
                    .usernameParameter("email")
                    .loginPage("/login")
                    .loginProcessingUrl("/loginProc")
                    .defaultSuccessUrl("/")
            )
            .oauth2Login(oAuth2LoginConfigurer ->
                oAuth2LoginConfigurer
                    .loginPage("/login")
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService)
            );
        return http.build();
    }
}
