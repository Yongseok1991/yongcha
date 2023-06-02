package yong.app.domain.post.category.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.post.category.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 전체적으로 transactional true걸기
public class YongPostCategoryServiceImpl implements YongPostCategoryService {

    private final YongPostCategoryRepository yongPostCategoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongPostCategoryVO> list() {

        List<YongPostCategory> postCategories = yongPostCategoryRepository.findAll();

        // 결과값이 없을때, 반환 타입이 컬렉션이면 '빈 컬렉션'이 리턴됨이 보장된다.
        // -> 그렇기 때문에 size가 0인 컬렉션이 반환된다.
        // => 따라서 '!= null'와 같은 작업을 하지 말것!
        // (추가) 단건 반환의 경우 결과 값이 없으면 null이 반환된다.
        if(postCategories.isEmpty()){
            throw new UsernameNotFoundException("there is no post category");
        }
        return postCategories.stream()
                .map(category -> modelMapper.map(category, YongPostCategoryVO.class))
                .collect(Collectors.toList());
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

        return modelMapper.map(yongPostCategory, YongPostCategoryVO.class);
    }

    @Override
    @Transactional(readOnly = false) // 변경이 필요한 메서드에서만 트랜잭션 readOnly false로 풀기
    public void delete(Long id) {
        YongPostCategory yongPostCategory = yongPostCategoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no category"));

        yongPostCategory.deleteCategory();
    }
}
