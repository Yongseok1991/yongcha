package yong.app.domain.post.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yong.app.global.response.ApiDocumentResponse;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "post comment 컨트롤러", description = "게시글에 대한 댓글 컨트롤러")
public class YongCommentController {

    private final YongCommentService yongCommentService;
    
    @Operation(summary = "show comment list", description = "전체 댓글 리스트")
    @ApiDocumentResponse
    @GetMapping("/comments")
    public StatusResponse list(){
        List<YongCommentVO> list = yongCommentService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 댓글 조회");
    }

    @Operation(summary = "insert comment", description = "댓글 삽입")
    @ApiDocumentResponse
    @PostMapping("/comments")
    public StatusResponse save(@RequestBody YongCommentDTO yongCommentDTO){
        Long joinId = yongCommentService.join(yongCommentDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "댓글 생성 성공");
    }

    @Operation(summary = "show comment view", description = "댓글 단건 조회")
    @ApiDocumentResponse
    @GetMapping("/comments/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongCommentVO show = yongCommentService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "댓글 단일건 조회");
    }

    @Operation(summary = "comment update", description = "댓글 업데이트")
    @ApiDocumentResponse
    @PutMapping("/comments/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongCommentDTO yongCommentDTO){
        yongCommentService.update(id, yongCommentDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    @Operation(summary = "comment delete", description = "댓글 삭제")
    @ApiDocumentResponse
    @DeleteMapping("/comments/{id}")
    public StatusResponse delete(@PathVariable("id") Long id){
        yongCommentService.delete(id);
        return new StatusResponse(StatusCode.SUCCESS);
    }
}
