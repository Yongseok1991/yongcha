package yong.app.global.response;

import lombok.*;
import org.springframework.validation.FieldError;

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
@NoArgsConstructor
@ToString
public class StatusResponse {

    private LocalTime timestamp = LocalTime.now();
    private Integer statusCode;
    private String statusName;
    private String message;
    private String moreMessage;
    private Object data;
    private List<FieldsError> validErrors = new ArrayList<>();        // for validation check errors

    // FOR 'RestApiException'
    public StatusResponse(StatusCode statusCode){
        this.statusCode = statusCode.getHttpStatus().value();
        this.statusName = statusCode.getHttpStatus().name();
        this.message = statusCode.getMessage();
        this.data = statusCode.getMoreMessage();
    }

    public StatusResponse(StatusCode statusCode, String moreMessage){
        this.statusCode = statusCode.getHttpStatus().value();
        this.statusName = statusCode.getHttpStatus().name();
        this.message = statusCode.getMessage();
        this.moreMessage = moreMessage;
    }

    // for 200 response
    public StatusResponse(StatusCode statusCode, Object data){
        this.statusCode = statusCode.getHttpStatus().value();
        this.statusName = statusCode.getHttpStatus().name();
        this.message = statusCode.getMessage();
        this.data = data;
    }

    public StatusResponse(StatusCode statusCode, Object data, String moreMessage){
        this.statusCode = statusCode.getHttpStatus().value();
        this.statusName = statusCode.getHttpStatus().name();
        this.message = statusCode.getMessage();
        this.moreMessage = moreMessage;
        this.data = data;
    }

    public void addError(String objectName, String field, String message) {
        validErrors.add(new FieldsError(objectName, field, message));
    }


    @Getter
    @AllArgsConstructor
    public static class FieldsError {
        private String objectName;
        private String field;
        private String message;
    }

}
