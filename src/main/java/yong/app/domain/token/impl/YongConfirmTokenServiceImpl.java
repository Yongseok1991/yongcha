package yong.app.domain.token.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.post.comment.YongCommentService;
import yong.app.domain.token.YongConfirmRespository;
import yong.app.domain.token.YongConfirmToken;
import yong.app.domain.token.YongConfirmTokenService;
import yong.app.domain.user.YongUser;
import yong.app.domain.user.YongUserRepository;
import yong.app.domain.user.YongUserService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongConfirmTokenServiceImpl implements YongConfirmTokenService {

    private final YongConfirmRespository yongConfirmRespository;
    private final YongUserRepository yongUserRepository;
    @Override
    @Transactional
    public Long save(Long yongUserId) {

        YongUser joinedUser = yongUserRepository.findById(yongUserId)
                .orElseThrow(IllegalStateException::new);

        String token = UUID.randomUUID().toString();

        YongConfirmToken yongConfirmToken =
                YongConfirmToken
                        .createTokenBuilder()
                        .token(token)
                        .confirmTime(LocalDateTime.now())
                        .expiredTime(LocalDateTime.now().plusMinutes(60))
                        .yongUser(joinedUser)
                        .build();
        return yongConfirmRespository.save(yongConfirmToken).getId();
    }

    @Override
    public Optional<YongConfirmToken> getToken(String token) {
        return yongConfirmRespository.findByToken(token);
    }

    @Override
    @Transactional
    public int changeConfirmedTime(String token) {
        return yongConfirmRespository.updateConfirmedAt(token, LocalDateTime.now());
    }


}
