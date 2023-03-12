package yong.app.com.vaildation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private List<FieldError> errors;

    public ErrorResponse() {
        this.errors = new ArrayList<>();
    }

    public void addError(String objectName, String field, String message) {
        errors.add(new FieldError(objectName, field, message));
    }

}
