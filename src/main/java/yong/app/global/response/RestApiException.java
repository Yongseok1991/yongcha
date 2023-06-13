package yong.app.global.response;

import lombok.Getter;

/**
 * @fileName RestApiException
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-04-30
 * @summary  발생한 예외를 처리해줄 에러 클래스
 *           -> 예외 상황에 사용하기 위한 Exception
 **/

@Getter
public class RestApiException extends RuntimeException {
    private final StatusCode statusCode;

    public RestApiException(StatusCode statusCode, Object moreMessage) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
        this.statusCode.setMoreMessage(moreMessage);
    }
    public RestApiException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }
}
