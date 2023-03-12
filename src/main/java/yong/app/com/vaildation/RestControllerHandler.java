package yong.app.com.vaildation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> bindException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("validation failed");
        if(bindingResult.hasErrors()) {
            for(FieldError fieldError : ex.getFieldErrors()) {
                errorResponse.addError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
