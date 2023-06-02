package yong.app.domain.token;

import java.util.Optional;

public interface YongConfirmTokenService {
    Long confirmTokenSave(Long yongUserId);
    Optional<YongConfirmToken> getToken(String token);
    int changeConfirmedTime(String Token);


}
