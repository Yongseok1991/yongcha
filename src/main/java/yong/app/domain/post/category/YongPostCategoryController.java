package yong.app.domain.post.category;

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
@Tag(name = "post category 컨트롤러", description = "게시글 카테고리 컨트롤러")
public class YongPostCategoryController {

    private final YongPostCategoryService yongPostCategoryService;

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAll -> vo로 변경
    @Operation(summary = "show post category list", description = "전체 게시글 카테고리 리스트")
    @ApiDocumentResponse
    @GetMapping("/categories")
    public StatusResponse list(){
        List<YongPostCategoryVO> listCategory = yongPostCategoryService.list();
        if(listCategory.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, listCategory, "전체 카테고리 조회");
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder 이용
    @Operation(summary = "insert post category", description = "게시글 카테고리 삽입")
    @ApiDocumentResponse
    @PostMapping("/categories")
    public StatusResponse join(@RequestBody YongPostCategoryDTO yongPostCategoryDTO){
        Long joinId = yongPostCategoryService.join(yongPostCategoryDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "카테고리 생성 성공");
    }

    // UPDATE
    // - 리턴 : void
    // - 방법 : findById -> 변경 메서드를 통해 '변경 지점이 엔티티로 모이도록' 하였다.
    @Operation(summary = "update post category", description = "게시글 카테고리 업데이트")
    @ApiDocumentResponse
    @PutMapping("/categories/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongPostCategoryDTO yongPostCategoryDTO){
        yongPostCategoryService.update(id, yongPostCategoryDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 VO로 변경
    @Operation(summary = "show post category view", description = "게시글 카테고리 단건 조회")
    @ApiDocumentResponse
    @GetMapping("/categories/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongPostCategoryVO findUser = yongPostCategoryService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, findUser, "카테고리 단일건 조회");
    }

    // DELETE
    // - 리턴 : void
    // - 방법 : findById -> delete (delete_yn 컬럼 to 'Y')
    @Operation(summary = "delete post category", description = "게시글 카테고리 삭제")
    @ApiDocumentResponse
    @DeleteMapping("/categories/{id}")
    public StatusResponse delete(@PathVariable("id") Long id){
        yongPostCategoryService.delete(id);
        return new StatusResponse(StatusCode.SUCCESS);
    }
}
