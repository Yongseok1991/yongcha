package yong.app.domain.moving.company;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class YongMovingCompanyController {

    private final YongMovingCompanyService yongMovingCompanyService;

    @GetMapping("/moving/companies")
    public StatusResponse list(){
        List<YongMovingCompanyVO> list = yongMovingCompanyService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 이사 업체 리스트 조회");
    }

    @PostMapping("/moving/companies")
    public StatusResponse insert(@RequestBody YongMovingCompanyDTO yongMovingCompanyDTO){
        Long joinId = yongMovingCompanyService.join(yongMovingCompanyDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "이사 업체 생성 성공");
    }

    @GetMapping("/moving/companies/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongMovingCompanyVO show = yongMovingCompanyService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "이사 업체 단건 조회");
    }

    @PutMapping("/moving/companies/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongMovingCompanyDTO yongMovingCompanyDTO){
        yongMovingCompanyService.update(id, yongMovingCompanyDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    @DeleteMapping("/moving/companies/{id}")
    public StatusResponse delete(@PathVariable("id") Long id){
        yongMovingCompanyService.delete(id);
        return new StatusResponse(StatusCode.SUCCESS);
    }
}
