package yong.app.domain.post.post;

import java.util.List;

public interface YongPostService {

    List<YongPostVO> list();

    List<YongPostVO> listWithFilesAndComments();

    Long join(YongPostDTO yongPostDTO);

    void update(Long id, YongPostDTO yongPostDTO);

    YongPostVO show(Long id);

    YongPostVO showWithFilesAndComments(Long id);

    void delete(Long id);

    List<YongPostVO> testQueryDSL();
}
