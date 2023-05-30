package yong.app.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @fileName StatusCode
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-05-01
 * @summary  StatusCode - status code enum 으로 정의
 **/

@Getter
public enum StatusCode {

    /*
     * 200 OK: 성공
     */
    SUCCESS(HttpStatus.OK, "Success.", ""),

    /*
    * 201 : CREATED
    * */
    CREATED(HttpStatus.CREATED, "Created", ""),

    /*
     * No content : 204
     */
    NO_CONTENT(HttpStatus.NO_CONTENT, "No Content", ""),

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request.", ""),

    /*
     * 401 UNAUTHORIZED: 인증되지 않은 사용자의 요청
     */
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "Unauthorized.", ""),

    /*
     * 403 FORBIDDEN: 권한이 없는 사용자의 요청
     */
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "Forbidden.", ""),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not found.", ""),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Not allowed method.", ""),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server error.", "");

    private final HttpStatus httpStatus;
    private final String message;
    private Object moreMessage;

    StatusCode(HttpStatus httpStatus, String message, Object moreMessage){
        this.httpStatus = httpStatus;
        this.message = message;
        this.moreMessage = moreMessage;
    }

    // 추가적으로 필요한 message에 대해서만 출력을 위해 setter 적용
    public void setMoreMessage(Object moreMessage){
        this.moreMessage = moreMessage;
    }
}