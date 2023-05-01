package yong.app.global.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;
import yong.app.global.response.StatusCode;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @fileName StatusResponse
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-05-01
 * @summary  status에 대한 반환
 *           - statusCode : 상태코드값
 *           - statusName : 상태코드 이름
 *           - message : 상태코드 메시지
 *           - moreMessage : 사용자가 추가로 입력하는 message
 *           - validErrors : validation error 있을 경우 -> list
 **/


@Getter
@Setter
public class StatusResponse {

    private LocalTime timestamp = LocalTime.now();
    private Integer statusCode;
    private String statusName;
    private String message;
    private Object moreMessage;
    private List<FieldError> validErrors;        // for validation check errors

    public StatusResponse() {
        this.validErrors = new ArrayList<>();
    }

    // FOR 'RestApiException'
    public StatusResponse(StatusCode statusCode){
        this.statusCode = statusCode.getHttpStatus().value();
        this.statusName = statusCode.getHttpStatus().name();
        this.message = statusCode.getMessage();
        this.moreMessage = statusCode.getMoreMessage();
    }

    // FOR 200 STATUS
    public StatusResponse(Integer statusCode, String statusName, String message, Object moreMessae){
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.message = message;
        this.moreMessage = moreMessae;
    }

    public void addError(String objectName, String field, String message) {
        validErrors.add(new FieldError(objectName, field, message));
    }

}
