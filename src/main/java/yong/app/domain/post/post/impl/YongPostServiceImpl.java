package yong.app.domain.post.post.impl;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.base.YongFileCommon;
import yong.app.domain.file.group.QYongFileGroupVO;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.file.group.YongFileGroupService;
import yong.app.domain.file.group.YongFileGroupVO;
import yong.app.domain.post.category.YongPostCategory;
import yong.app.domain.post.category.YongPostCategoryService;
import yong.app.domain.post.comment.QYongCommentVO;
import yong.app.domain.post.comment.YongCommentService;
import yong.app.domain.post.comment.YongCommentVO;
import yong.app.domain.post.post.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.querydsl.core.group.GroupBy.groupBy;
import static yong.app.domain.file.group.QYongFileGroup.yongFileGroup;
import static yong.app.domain.post.comment.QYongComment.yongComment;
import static yong.app.domain.post.post.QYongPost.yongPost;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class YongPostServiceImpl implements YongPostService {

    private final YongFileGroupService yongFileGroupService;
    private final YongCommentService yongCommentService;
    private final YongPostCategoryService yongPostCategoryService;
    private final JPAQueryFactory jpaQueryFactory;
    private final YongPostRepository yongPostRepository;
    private final YongFileCommon yongFileCommon;

    @Override
    public List<YongPostVO> list() {
        List<YongPost> all = yongPostRepository.findAll();
        if(all.isEmpty()) throw new NullPointerException("post is empty");
        return all.stream().map(yongPost -> new YongPostVO(yongPost, new ArrayList<>(), null)).toList();
    }

    @Override
    public List<YongPostVO> listWithFilesAndComments() {
        List<YongPost> all = yongPostRepository.findAll();
        if(all.isEmpty()) throw new NullPointerException("post is empty");
        return all.stream().map(yongPost -> {
            YongFileGroupVO fileGroup = null;
            if (yongPost.getYongFileGroupId() != null) {
                fileGroup = yongFileGroupService.findFileGroupWithFiles(yongPost.getYongFileGroupId());
            }
            List<YongCommentVO> comments = yongCommentService.findAllCommentsByPostId(yongPost.getId());
            return new YongPostVO(yongPost, comments, fileGroup);
        }).toList();
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
        YongPostCategory findParentCategory = yongPostCategoryService.findPostCategoryByPostId(yongPostDTO.getYongPostCategoryId());
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
        YongPostCategory findParentCategory = yongPostCategoryService.findPostCategoryByPostId(yongPostDTO.getYongPostCategoryId());
//        yongPostDTO.setYongPostCategory(findParentCategory);


        // 2. if has file
        YongFileGroup fileGroup = null;
        if(yongPostDTO.getYongFileGroupId() != null){
            fileGroup = yongFileCommon.addFiles(yongPostDTO.getAddFiles(), yongPostDTO.getDelFiles(), yongPostDTO.getYongFileGroupId());
//            yongPostDTO.setYongFileGroup(fileGroup);
        }else {  // 2-1. insert file new!
            fileGroup = yongFileCommon.addFiles(yongPostDTO.getAddFiles(), null, 0L);
//            yongPostDTO.setYongFileGroup(fileGroup);
        }

        // 3. then use update method
        YongPost findPost = yongPostRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no post"));
        findPost.updatePost(yongPostDTO, findParentCategory, fileGroup);
    }

    @Override
    public YongPostVO show(Long id) {
        YongPost findPost = yongPostRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no post"));

        return new YongPostVO(findPost,  new ArrayList<>(), null);
    }

    @Override
    public YongPostVO showWithFilesAndComments(Long id) {
        YongPost findPost = yongPostRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no post"));
        YongFileGroupVO yongFileGroupVO = null;
        if(findPost.getYongFileGroupId() != null) yongFileGroupVO = yongFileGroupService.findFileGroupWithFiles(findPost.getYongFileGroupId());
        List<YongCommentVO> commentVOS = yongCommentService.findAllCommentsByPostId(findPost.getId());
        return new YongPostVO(findPost, commentVOS, yongFileGroupVO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        YongPost findPost = yongPostRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no post"));
        findPost.deletePost();
    }

    @Override
    public List<YongPostVO> testQueryDSL() {
        Map<Long, YongPostVO> transform = jpaQueryFactory
                .from(yongComment)
                .leftJoin(yongComment.yongPost, yongPost)
                .leftJoin(yongFileGroup).on(yongPost.yongFileGroupId.eq(yongFileGroup.id))
                .transform(groupBy(yongPost.id).as(
                        new QYongPostVO(yongPost.id, yongPost.title, yongPost.content,
                                GroupBy.list(new QYongCommentVO(yongComment.id, yongComment.title, yongComment.content, yongComment.deleteYn)),
                                yongPost.deleteYn, yongPost.viewCount,
                                new QYongFileGroupVO(yongFileGroup.id, yongFileGroup.fileGroupName, yongFileGroup.description)
                        )));

        return transform.keySet().stream().map(transform::get).toList();
    }
}