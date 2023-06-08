package yong.app.domain.post.category.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.post.category.*;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 전체적으로 transactional true걸기
public class YongPostCategoryServiceImpl implements YongPostCategoryService {

    private final YongPostCategoryRepository yongPostCategoryRepository;

    @Override
    public List<YongPostCategoryVO> list() {
        List<YongPostCategory> postCategories = yongPostCategoryRepository.findAll();
        if(postCategories.isEmpty()) throw new UsernameNotFoundException("there is no post category");
        return postCategories.stream().map(YongPostCategoryVO::new).toList();
    }

    @Override
    @Transactional(readOnly = false)  // 변경이 필요한 메서드에서만 트랜잭션 readOnly false로 풀기
    public Long join(YongPostCategoryDTO yongPostCategoryDTO) {

        YongPostCategory yongPostCategory = YongPostCategory.insertPostCategory()
                .description(yongPostCategoryDTO.getDescription())
                .name(yongPostCategoryDTO.getName())
                .build();

        YongPostCategory savePostCategory = yongPostCategoryRepository.save(yongPostCategory);
        return savePostCategory.getId();
    }

    @Override
    @Transactional(readOnly = false)  // 변경이 필요한 메서드에서만 트랜잭션 readOnly false로 풀기
    public void update(Long id, YongPostCategoryDTO yongPostCategoryDTO) {
        YongPostCategory yongPostCategory = yongPostCategoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no post category"));
        yongPostCategory.updateCategory(yongPostCategoryDTO);
    }

    @Override
    public YongPostCategoryVO show(Long id) {
        YongPostCategory yongPostCategory = yongPostCategoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no post category"));
        return new YongPostCategoryVO(yongPostCategory);
    }

    @Override
    @Transactional(readOnly = false) // 변경이 필요한 메서드에서만 트랜잭션 readOnly false로 풀기
    public void delete(Long id) {
        YongPostCategory yongPostCategory = yongPostCategoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no category"));
        yongPostCategory.deleteCategory();
    }

    @Override
    public YongPostCategory findPostCategoryByPostId(Long postId) {
        return yongPostCategoryRepository.findByIdAndDeleteYnIs(postId, "N")
                .orElseThrow(() -> new NoSuchElementException("there is no parent"));
    }
}

