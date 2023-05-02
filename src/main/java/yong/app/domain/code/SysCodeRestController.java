package yong.app.domain.code;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import yong.app.global.response.ApiDocumentResponse;
import yong.app.global.response.StatusCode;
import yong.app.global.response.RestApiException;
import yong.app.global.response.StatusResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
* @fileName SysCodeRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-04-28
* @summary   sysCode RestController (view, insert, update, delete, list all view)
 *           @ApiDocumentResponse : 중복되는 ApiResponse를 하나에 모아서 정리
 *           RestApiException : error/status에 대해서 예외 처리 (code값, 추가 message)
**/

@Slf4j
@Tag(name="SysCode 컨트롤러", description="SysCode 컨트롤러 관련 api입니다.")              // ** 같은 Tag name에 대해서는 같은 api 그룹으로 인식
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class SysCodeRestController {
    private final SysCodeRepository sysCodeRepository;
    private final SysCodeService sysCodeService;

    @Operation(summary = "show SysCode list", description = "SysCode list를 보여줍니다.")   // ** api 동작에 대한 명세를 적는 어노테이션
    @ApiDocumentResponse
    @GetMapping("/sysCodes")// sys-codes
    @Transactional(readOnly = true)
    public ResponseEntity index() {
        List<SysCode> list = sysCodeRepository.findAll();

        if(list.size() == 0){
            throw new RestApiException(StatusCode.NO_CONTENT, "there is no list");
        }

        return ResponseEntity
                .status(StatusCode.SUCCESS.getHttpStatus().value())
                .body(new StatusResponse(StatusCode.SUCCESS, list));
    }

    @Operation(summary = "show SysCode view", description = "SysCode의 특정 id에 대한 view를 보여줍니다.")
    @ApiDocumentResponse
    @GetMapping("/sysCodes/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity view(@PathVariable("id") Long id) {
        Optional<SysCode> optional = sysCodeRepository.findById(id);

        if(!optional.isPresent()) {
            optional.orElseThrow(() -> new RestApiException(StatusCode.BAD_REQUEST, "GetMapping Error"));
        }

        Optional<SysCode> sysCode = sysCodeRepository.findById(id);
        return ResponseEntity
                .status(StatusCode.SUCCESS.getHttpStatus().value())
                .body(new StatusResponse(StatusCode.SUCCESS, sysCode.get()));
    }

    @Operation(summary = "update SysCode", description = "SysCode의 id를 통해 특정 code를 update합니다.")   // ** api 동작에 대한 명세를 적는 어노테이션
    @ApiDocumentResponse
    @PutMapping("/sysCodes/{id}")
    public ResponseEntity update(@RequestBody @PathVariable("id")Long id, @RequestBody @Valid SysCodeDTO sysCodeDTO) {
        SysCode sysCode = sysCodeService.updateById(id, sysCodeDTO);
        return ResponseEntity
                .status(StatusCode.SUCCESS.getHttpStatus().value())
                .body(new StatusResponse(StatusCode.SUCCESS, sysCode));
    }


    @Operation(summary = "delete SysCode", description = "SysCode의 id를 통해 특정 code를 delete합니다.")
    @ApiDocumentResponse
    @DeleteMapping("/sysCodes/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<SysCode> optional = sysCodeRepository.findById(id);

        if(!optional.isPresent()) {
            optional.orElseThrow(() -> new RestApiException(StatusCode.BAD_REQUEST, "DeleteMapping Error"));
        }

        sysCodeRepository.deleteById(id);
        return ResponseEntity
                .status(StatusCode.SUCCESS.getHttpStatus().value())
                .body(new StatusResponse(StatusCode.SUCCESS, "delete success"));
    }

    @Operation(summary = "insert SysCode", description = "SysCode에 데이터를 insert합니다.")
    @ApiDocumentResponse
    @PostMapping("/sysCodes")
    public ResponseEntity insert(@RequestBody @Valid SysCodeDTO sysCodeDTO){
        SysCode sysCode = sysCodeService.save(sysCodeDTO);
        return ResponseEntity
                .status(StatusCode.SUCCESS.getHttpStatus().value())
                .body(new StatusResponse(StatusCode.SUCCESS, sysCode));
    }
}
