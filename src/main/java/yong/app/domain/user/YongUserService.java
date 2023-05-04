package yong.app.domain.user;

import java.util.List;
import java.util.UUID;

public interface YongUserService {
    YongUser joinProc(YongAuthor authorEnum, YongUserDTO bean);
    YongUser oAuthJoinProc(YongAuthor authorEnum, YongUserDTO bean);
    YongUser findByUsername(String username);
    List<YongUser> findAll();

    Boolean checkUsernameDuplicate(String username);
}
