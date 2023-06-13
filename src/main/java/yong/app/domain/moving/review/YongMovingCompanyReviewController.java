package yong.app.domain.moving.review;

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
@Tag(name = "moving company review 컨트롤러", description = "이사 업체에 대한 리뷰 컨트롤러")
public class YongMovingCompanyReviewController {

    private final YongMovingCompanyReviewService yongMovingCompanyReviewService;

    @Operation(summary = "show moving company review list", description = "전체 이사 업체 리뷰 리스트")
    @ApiDocumentResponse
    @GetMapping("/company/reviews")
    public StatusResponse list(){
        List<YongMovingCompanyReviewVO> list = yongMovingCompanyReviewService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "이사 업체 리뷰 리스트 조회");
    }

    @Operation(summary = "show moving company review with files", description = "전체 이사 업체 리뷰 & 파일들 리스트")
    @ApiDocumentResponse
    @GetMapping("/company/reviews/with/files")
    public StatusResponse listWithFiles(){
        List<YongMovingCompanyReviewVO> list = yongMovingCompanyReviewService.listWithFiles();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "이사 업체 리뷰 with files 리스트 조회");
    }
    
    @Operation(summary = "insert moving company review", description = "이사 업체 리뷰 삽입")
    @ApiDocumentResponse
    @PostMapping("/company/reviews")
    public StatusResponse insert(@RequestBody YongMovingCompanyReviewDTO yongMovingCompanyReviewDTO){
        Long joinId = yongMovingCompanyReviewService.join(yongMovingCompanyReviewDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "이사 업체 리뷰 등록 성공");
    }

    @Operation(summary = "show moving company review view", description = "이사 업체 리뷰 단건 조회")
    @ApiDocumentResponse
    @GetMapping("/company/reviews/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongMovingCompanyReviewVO show = yongMovingCompanyReviewService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "이사 업체 리뷰 단건 조회");
    }

    @Operation(summary = "show moving company review view with files", description = "이사 업체 리뷰 & 파일들 단건 조회")
    @ApiDocumentResponse
    @GetMapping("/company/reviews/with/files/{id}")
    public StatusResponse showWithFiles(@PathVariable("id") Long id){
        YongMovingCompanyReviewVO show = yongMovingCompanyReviewService.showWithFiles(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "이사 업체 리뷰 with files 단건 조회");
    }

    @Operation(summary = "update moving company review", description = "이사 업체 리뷰 업데이트")
    @ApiDocumentResponse
    @PutMapping("/company/reviews/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongMovingCompanyReviewDTO yongMovingCompanyReviewDTO){
        yongMovingCompanyReviewService.update(id, yongMovingCompanyReviewDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    @Operation(summary = "delete moving company review", description = "이사 업체 리뷰 삭제")
    @ApiDocumentResponse
    @DeleteMapping("/company/reviews/{id}")
    public StatusResponse delete(@PathVariable("id") Long id){
        yongMovingCompanyReviewService.delete(id);
        return new StatusResponse(StatusCode.SUCCESS);
    }

}
