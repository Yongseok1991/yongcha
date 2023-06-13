package yong.app.domain.moving.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yong.app.global.response.ApiDocumentResponse;
import yong.app.global.response.RestApiException;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "moving company 컨트롤러", description = "이사 업체 컨트롤러")
public class YongMovingCompanyController {

    private final YongMovingCompanyService yongMovingCompanyService;

    @Operation(summary = "show moving company list", description = "전체 이사 업체 리스트")
    @ApiDocumentResponse
    @GetMapping("/moving/companies")
    public StatusResponse list(){
        List<YongMovingCompanyVO> list = yongMovingCompanyService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 이사 업체 리스트 조회");
    }

    @Operation(summary = "show moving company list with files", description = "전체 이사 업체 & 파일들 리스트")
    @ApiDocumentResponse
    @GetMapping("/moving/companies/with/files")
    public StatusResponse listWithFiles(){
        List<YongMovingCompanyVO> list = yongMovingCompanyService.listWithFiles();
        if(list.isEmpty()) throw new RestApiException(StatusCode.NO_CONTENT, "리스트가 비었습니다.");
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 이사 업체 with 파일 리스트 조회");
    }

    @Operation(summary = "insert moving company", description = "이사 업체 삽입")
    @ApiDocumentResponse
    @PostMapping("/moving/companies")
    public StatusResponse insert(@RequestBody YongMovingCompanyDTO yongMovingCompanyDTO){
        Long joinId = yongMovingCompanyService.join(yongMovingCompanyDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "이사 업체 생성 성공");
    }

    @Operation(summary = "show moving company view", description = "이사 업체 단건 조회")
    @ApiDocumentResponse
    @GetMapping("/moving/companies/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongMovingCompanyVO show = yongMovingCompanyService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "이사 업체 단건 조회");
    }
    
    @Operation(summary = "show moving company view with files", description = "이사 업체 & 파일들 단건 조회")
    @ApiDocumentResponse
    @GetMapping("/moving/companies/with/files/{id}")
    public StatusResponse showWithFiles(@PathVariable("id") Long id){
        YongMovingCompanyVO show = yongMovingCompanyService.showWithFiles(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "이사 업체 단건 조회");
    }

    @Operation(summary = "update moving company", description = "이사 업체 업데이트")
    @ApiDocumentResponse
    @PutMapping("/moving/companies/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongMovingCompanyDTO yongMovingCompanyDTO){
        yongMovingCompanyService.update(id, yongMovingCompanyDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    @Operation(summary = "delete moving company", description = "이사 업체 삭제")
    @ApiDocumentResponse
    @DeleteMapping("/moving/companies/{id}")
    public StatusResponse delete(@PathVariable("id") Long id){
        yongMovingCompanyService.delete(id);
        return new StatusResponse(StatusCode.SUCCESS);
    }
}
