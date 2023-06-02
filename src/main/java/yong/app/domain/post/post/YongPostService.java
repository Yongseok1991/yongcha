package yong.app.domain.post.post;

import java.util.List;

public interface YongPostService {

    List<YongPostVO> list();

    Long join(YongPostDTO yongPostDTO);

    void update(Long id, YongPostDTO yongPostDTO);

    YongPostVO show(Long id);

    void delete(Long id);

//    YongPost findPost(Long id);
}
