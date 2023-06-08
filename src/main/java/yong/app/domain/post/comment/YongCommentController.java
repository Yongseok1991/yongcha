package yong.app.domain.post.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yong.app.domain.post.comment.Impl.YongCommentServiceImpl;
import yong.app.domain.post.post.YongPost;
import yong.app.domain.post.post.YongPostVO;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YongCommentController {

    private final YongCommentService yongCommentService;

    @GetMapping("/comments")
    public StatusResponse list(){
        List<YongCommentVO> list = yongCommentService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 댓글 조회");
    }

    @PostMapping("/comments")
    public StatusResponse save(@RequestBody YongCommentDTO yongCommentDTO){
        Long joinId = yongCommentService.join(yongCommentDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "댓글 생성 성공");
    }

    @GetMapping("/comments/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongCommentVO show = yongCommentService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "댓글 단일건 조회");
    }

    @PutMapping("/comments/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongCommentDTO yongCommentDTO){
        yongCommentService.update(id, yongCommentDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    @DeleteMapping("/comments/{id}")
    public StatusResponse delete(@PathVariable("id") Long id){
        yongCommentService.delete(id);
        return new StatusResponse(StatusCode.SUCCESS);
    }
}
