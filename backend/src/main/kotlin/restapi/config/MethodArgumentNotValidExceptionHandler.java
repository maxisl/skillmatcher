package restapi.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import restapi.data.ErrorResult;

import java.util.List;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult parameterExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult exceptions = e.getBindingResult();
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {

                FieldError fieldError = (FieldError) errors.get(0);
                return new ErrorResult(-1,fieldError.getDefaultMessage());
            }
        }

        return new ErrorResult(-1,"argument valid failure");
    }
}

//@Null restrict property can only be null.
//@NotNull restrict property is not nullable.
//@Max(value) restrict property must be a number not greater than the specified value.
//@Min(value) restrict property must be a number not less than the specified value.
//@Digits(integer, fraction) restrict property must be a decimal, and the number of digits in the integer part cannot exceed integer, and the number of digits in the decimal part cannot exceed fraction.
//@Past restrict property must be a past date.
//@Future restrict property must be a future date.
//@Pattern(value) restrict property must conform to the specified regular expression.
//@Size(max, min) limit character length must be between min and Max.
//@NotEmpty valid property value is not nullable, the string length is not 0, and the collection size is not 0.
//@NotBlank valid property value is not null, and after trim() the length is not 0.
//@Email Verify that the attribute value is a string in email format.