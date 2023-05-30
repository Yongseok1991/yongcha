package yong.app.domain.post.category;

import java.util.List;

public interface YongPostCategoryService {

    List<YongPostCategoryVO> list();

    Long join(YongPostCategoryDTO yongPostCategoryDTO);

    void update(Long id, YongPostCategoryDTO yongPostCategoryDTO);

    YongPostCategoryVO show(Long id);

    void delete(Long id);
}
