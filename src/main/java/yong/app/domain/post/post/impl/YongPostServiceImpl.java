package yong.app.domain.post.post.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.file.YongFile;
import yong.app.domain.file.YongFileRepository;
import yong.app.domain.file.YongFileVO;
import yong.app.domain.post.category.YongPostCategory;
import yong.app.domain.post.category.YongPostCategoryRepository;
import yong.app.domain.post.post.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class YongPostServiceImpl implements YongPostService {

    private final YongPostRepository yongPostRepository;
    private final YongPostCategoryRepository yongPostCategoryRepository;
    private final YongFileRepository yongFileRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongPostVO> list() {
        List<YongPost> findAll = yongPostRepository.findAll(); // -> fetch join

        return findAll.stream().map(post -> modelMapper.map(post, YongPostVO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongPostDTO yongPostDTO) {

        if(yongPostDTO.getYongPostCategoryId() == null) throw new NullPointerException("parent can not be null");

        // 1. post builder
        YongPost yongPost = YongPost.insertPostBuilder()
                .title(yongPostDTO.getTitle())
                .content(yongPostDTO.getContent())
                .viewCount(yongPostDTO.getViewCount())
                .build();

        // 2. parent id -> find it -> if parent is not null, then set
        YongPostCategory findParentCategory = yongPostCategoryRepository.findByIdAndDeleteYnIs(yongPostDTO.getYongPostCategoryId(), "N")
                .orElseThrow(() -> new NoSuchElementException("there is no parent"));
        yongPost = yongPost.toBuilder().postCategory(findParentCategory).build();


        // 3. file id -> find it -> if is not null, then set
        if(yongPostDTO.getYongFileId() != null) {
            YongFile findFile = yongFileRepository.findByIdAndDeleteYnIs(yongPostDTO.getYongFileId(), "N")
                    .orElseThrow(() -> new NoSuchElementException("there is no file"));
            yongPost = yongPost.toBuilder().yongFile(findFile).build();
        }

        // 4. save it
        YongPost savePost = yongPostRepository.save(yongPost);

        return savePost.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongPostDTO yongPostDTO) {
        // 1. if has parent -> find parent and set parent
        YongPostCategory findParentCategory = yongPostCategoryRepository.findByIdAndDeleteYnIs(yongPostDTO.getYongPostCategoryId(), "N")
                .orElseThrow(() -> new NoSuchElementException("there is no parent"));
        yongPostDTO.setYongPostCategory(findParentCategory);

        // 2. if has file -> find file and set file
        if(yongPostDTO.getYongFileId() != null){
            YongFile findFile = yongFileRepository.findByIdAndDeleteYnIs(yongPostDTO.getYongFileId(), "N")
                    .orElseThrow(() -> new NoSuchElementException("there is no file"));
            yongPostDTO.setYongFile(findFile);
        }

        // 2. then use update method
        YongPost findPost = yongPostRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no post"));
        findPost.updatePost(yongPostDTO);
    }

    @Override
    public YongPostVO show(Long id) {
        YongPost findPost = yongPostRepository.findPostById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no post"));;
        return modelMapper.map(findPost, YongPostVO.class);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        YongPost findPost = yongPostRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no post"));
        findPost.deletePost();
    }
}