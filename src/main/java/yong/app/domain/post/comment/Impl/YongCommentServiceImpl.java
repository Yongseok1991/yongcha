package yong.app.domain.post.comment.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.post.comment.*;
import yong.app.domain.post.post.YongPost;
import yong.app.domain.post.post.YongPostRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongCommentServiceImpl implements YongCommentService {

    private final YongCommentRepository yongCommentRepository;
    private final YongPostRepository yongPostRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongCommentVO> list() {
        List<YongComment> yongComments = yongCommentRepository.findAll();
        return yongComments.stream().map(yongComment -> modelMapper.map(yongComment, YongCommentVO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongCommentDTO yongCommentDTO) {

        if(yongCommentDTO.getYongPostId() == null) throw new NullPointerException("post id is null!");

        YongPost parentPost = yongPostRepository.findByIdAndDeleteYnIs(yongCommentDTO.getYongPostId(), "N")
                .orElseThrow(() -> new NoSuchElementException("there is no post"));

        YongComment yongComment = YongComment.insertCommentBuilder()
                .yongPost(parentPost)
                .title(yongCommentDTO.getTitle())
                .content(yongCommentDTO.getContent())
                .build();

        YongComment save = yongCommentRepository.save(yongComment);
        return save.getId();
    }

    @Override
    public YongCommentVO show(Long id) {
        YongComment yongComment = yongCommentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no comment"));
        return modelMapper.map(yongComment, YongCommentVO.class);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongCommentDTO yongCommentDTO) {

        YongComment yongComment = yongCommentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no comment"));

        yongComment.updateComment(yongCommentDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        YongComment yongComment = yongCommentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no comment"));
        yongComment.deleteComment();
    }


}
