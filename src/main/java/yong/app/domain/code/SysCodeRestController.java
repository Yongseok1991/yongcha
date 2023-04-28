package yong.app.domain.code;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import yong.app.global.vaildation.ErrorResponse;
import yong.app.global.vaildation.RestControllerHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* @fileName SysCodeRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-04-28
* @summary   sysCode RestController (view, insert, update, delete, list all view)
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
    // ** HTTP 상태 코드에 대해 반환 정보 설정
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SysCode.class))),       // YongUser class가 ResponseBody에 반환
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),       // Error class가 ResponseBody에 반환 (후에 커스터마이징을 통해 error 반환 바꾸기)
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/sysCodes")
    @Transactional(readOnly = true)
    public ResponseEntity index() {
//        List<SysCode> list = sysCodeRepository.findAll();
//        Optional<SysCode> syscode = sysCodeRepository.findById(1).or
//        List<SysCodeDTO> list2 = list.stream().map(SysCodeDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(sysCodeRepository.findAll());
    }

    @Operation(summary = "show SysCode view", description = "SysCode의 특정 id에 대한 view를 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SysCode.class))),       // YongUser class가 ResponseBody에 반환
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),       // Error class가 ResponseBody에 반환 (후에 커스터마이징을 통해 error 반환 바꾸기)
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content)
    })
    @GetMapping("/sysCodes/{id}")
    public ResponseEntity view(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sysCodeRepository.findById(id));
    }


    @Operation(summary = "update SysCode", description = "SysCode의 id를 통해 특정 code를 update합니다.")   // ** api 동작에 대한 명세를 적는 어노테이션
    // ** HTTP 상태 코드에 대해 반환 정보 설정
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공", content = @Content(schema = @Schema(implementation = SysCode.class))),       // YongUser class가 ResponseBody에 반환
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),       // Error class가 ResponseBody에 반환 (후에 커스터마이징을 통해 error 반환 바꾸기)
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content)
    })
    @PutMapping("/sysCodes/{id}")
    public ResponseEntity update(@RequestBody @PathVariable("id")Long id, @RequestBody SysCodeDTO sysCodeDTO) {
        return ResponseEntity.ok(sysCodeService.updateById(id, sysCodeDTO));
    }


    @Operation(summary = "delete SysCode", description = "SysCode의 id를 통해 특정 code를 delete합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공", content = @Content(schema = @Schema(implementation = SysCode.class))),              // YongUser class가 ResponseBody에 반환
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),       // Error class가 ResponseBody에 반환 (후에 커스터마이징을 통해 error 반환 바꾸기)
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "SERVER ERROR", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/sysCodes/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        sysCodeRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "insert SysCode", description = "SysCode에 데이터를 insert합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "insert 성공", content = @Content(schema = @Schema(implementation = SysCode.class))),            // YongUser class가 ResponseBody에 반환
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),       // Error class가 ResponseBody에 반환 (후에 커스터마이징을 통해 error 반환 바꾸기)
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content),
            @ApiResponse(responseCode = "500", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/sysCodes")
    public ResponseEntity insert(@RequestBody SysCodeDTO sysCodeDTO){
        return ResponseEntity.ok(sysCodeService.save(sysCodeDTO));
    }

}
