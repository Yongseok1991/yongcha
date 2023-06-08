package yong.app.domain.post.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class YongPostController {

    private final YongPostService yongPostService;
    @GetMapping("/test/querydsl")
    public StatusResponse querydsl(){
        List<YongPostVO> list = yongPostService.testQueryDSL();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 게시글 조회 by querydsl");
    }

    @GetMapping("/posts")
    public StatusResponse list(){
        List<YongPostVO> list = yongPostService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 게시글 조회");
    }


    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAlll -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/posts/with/files/comments")
    public StatusResponse listWithFilesAndComments(){
        List<YongPostVO> list = yongPostService.listWithFilesAndComments();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 게시글 조회 with 댓글, 파일들");
    }


    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder 이용 -> 무조건 부모가 있어야함 / file의 경우 있고 없고를 분기처리
    //         (if) file 있다면 -> fileAdd with parent
    @PostMapping("/posts")
    public StatusResponse insert(@RequestBody YongPostDTO yongPostDTO){
        Long joinId = yongPostService.join(yongPostDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "게시글 생성 성공");
    }


    // UPDATE
    // - 리턴 : void
    // - 방법 : findById -> 변경 메서드를 통해 '변경 지점이 엔티티로 모이도록' 하였다. (file의 경우 있고 없고를 분기처리)
    //          (1) file 있다면 -> parentFileId 있는지 유무로 분기
    //          (2) parentFileId 있다면 -> 해당 parentId를
    @PutMapping("/posts/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongPostDTO yongPostDTO){
        yongPostService.update(id, yongPostDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }


    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 VO로 변경
    @GetMapping("/posts/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongPostVO findPost = yongPostService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, findPost, "게시글 단건 조회");
    }

    @GetMapping("/posts/with/files/comments/{id}")
    public StatusResponse showWithFilesAndComments(@PathVariable("id") Long id){
        YongPostVO findPost = yongPostService.showWithFilesAndComments(id);
        return new StatusResponse(StatusCode.SUCCESS, findPost, "게시글 단건 조회 with 댓글,파일들");
    }

    // DELETE
    // - 리턴 : void
    // - 방법 : findById -> delete (delete_yn 컬럼 to 'Y')
    @DeleteMapping("/posts/{id}")
    public StatusResponse delete(@PathVariable("id") Long id){
        yongPostService.delete(id);
        return new StatusResponse(StatusCode.SUCCESS);
    }

}
