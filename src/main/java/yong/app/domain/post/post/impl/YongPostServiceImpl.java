package yong.app.domain.post.post.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.base.YongFileCommon;
import yong.app.domain.file.file.YongFile;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.post.category.YongPostCategory;
import yong.app.domain.post.category.YongPostCategoryRepository;
import yong.app.domain.post.post.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class YongPostServiceImpl implements YongPostService {

    private final YongPostRepository yongPostRepository;
    private final YongPostCategoryRepository yongPostCategoryRepository;
    private final YongFileCommon yongFileCommon;
    private final ModelMapper modelMapper;

    @Override
    public List<YongPostVO> list() {

        List<YongPost> findAll = yongPostRepository.findAll(); // -> fetch join
        for (YongPost yongPost : findAll) {
            System.out.println("yongPost = " + yongPost);
        }
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

        // 3. if has file to save ...
        if(!yongPostDTO.getAddFiles().isEmpty()){
            YongFileGroup fileGroup = yongFileCommon.addFiles(yongPostDTO.getAddFiles(), null,0L);
            yongPost = yongPost.toBuilder().yongFileGroupId(fileGroup.getId()).build();
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


        // 2. if has file
        if(yongPostDTO.getYongFileGroupId() != null){
            YongFileGroup fileGroup = yongFileCommon.addFiles(yongPostDTO.getAddFiles(), yongPostDTO.getDelFiles(), yongPostDTO.getYongFileGroupId());
            yongPostDTO.setYongFileGroup(fileGroup);
        }else if(!yongPostDTO.getAddFiles().isEmpty()){  // 2-1. insert file new!
            YongFileGroup fileGroup = yongFileCommon.addFiles(yongPostDTO.getAddFiles(), null, 0L);
            yongPostDTO.setYongFileGroup(fileGroup);
        }

        // 3. then use update method
        YongPost findPost = yongPostRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no post"));
        findPost.updatePost(yongPostDTO);
    }

    @Override
    public YongPostVO show(Long id) {
        YongPost findPost = yongPostRepository.findById(id)
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

//    @Override
//    public YongPost findPost(Long id) {
//        YongPost parentPost = yongPostRepository.findByIdAndDeleteYnIs(yongCommentDTO.getYongPostId(), "N")
//                .orElseThrow(() -> new NoSuchElementException("there is no post"));
//        return null;
//    }
}