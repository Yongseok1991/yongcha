package yong.app.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import yong.app.global.auth.PrincipalDetails;

import java.util.Optional;


/**
* @fileName AuditorAwareImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-05-02
* @summary 등록자, 수정자 자동 바인딩 타입을 바꿔서 사용할 수 있음 ex) String, Long, Integer
**/
@Slf4j
public class AuditorAwareImpl implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        /*
        *       TODO
        *         regId, updtID가 ... 사용하는 곳만 바인딩하기 (보류)
         */
        if(authentication != null && !"anonymousUser".equals(authentication.getPrincipal())) {

            PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
            return Optional.of(details.getUser().getUid());             // ofNullable : return null

        } else {
            return null;
            // TODO : redirect to login page.... (move Back/to login page)
        }
    }
}
