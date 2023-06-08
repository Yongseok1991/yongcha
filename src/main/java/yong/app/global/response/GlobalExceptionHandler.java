package yong.app.global.response;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @fileName GlobalExceptionHandler
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-04-30
 * @summary   ConstraintViolationException : Exception check
 *            RestApiException : status response
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     *  > vaidation check
     *  - MethodArgumentNotValidException : 'DTO'에 붙은 validation에 대해 핸들링
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected StatusResponse handleValidationException(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();

        StatusResponse statusResponse = new StatusResponse(StatusCode.FORBIDDEN_ACCESS, "벨리데이션 오류야");
        if(bindingResult.hasErrors()) {
            for(FieldError fieldError : bindingResult.getFieldErrors()) {
                statusResponse.addError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return statusResponse;
    }

    @ExceptionHandler(BindException.class)
    protected StatusResponse handleValidationException(BindException ex){
        BindingResult bindingResult = ex.getBindingResult();

        StatusResponse statusResponse = new StatusResponse(StatusCode.BAD_REQUEST, "벨리데이션 오류야");
        if(bindingResult.hasErrors()) {
            for(FieldError fieldError : bindingResult.getFieldErrors()) {
                statusResponse.addError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return statusResponse;
    }

    /*
     *  > vaidation check
     *  - ConstraintViolationException : 'Entity'에 붙은 validation에 대해 핸들링
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected StatusResponse validationException(ConstraintViolationException e){
        StatusResponse statusResponse = new StatusResponse(StatusCode.BAD_REQUEST, "벨리데이션 오류야");
        if(!e.getConstraintViolations().isEmpty()) {
            for(ConstraintViolation constraintViolation : e.getConstraintViolations()) {
                // object-name , field, message
                // constraintViolation.getRootBean().toString() : vo값들
                statusResponse.addError(constraintViolation.getPropertyPath().toString(), constraintViolation.getRootBeanClass().getName(), constraintViolation.getMessage());
            }
        }

        return statusResponse;
    }

    /*
     * Developer Custom Exception: 직접 정의한 RestApiException 에러 클래스에 대한 예외 처리
     */
    @ExceptionHandler(RestApiException.class)
    protected StatusResponse handleCustomException(RestApiException ex) {
        StatusCode statusCode = ex.getStatusCode();
        return handleExceptionInternal(statusCode);
    }

    /*
     * handleExceptionInternal() 메소드를 오버라이딩해 응답 커스터마이징
     */
    private StatusResponse handleExceptionInternal(StatusCode statusCode) {
        return new StatusResponse(statusCode);
    }
}
