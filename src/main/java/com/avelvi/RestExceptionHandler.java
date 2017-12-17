package com.avelvi;

import com.avelvi.ws.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleControllerException(Exception exception) {
        HttpStatus status = resolveAnnotatedResponseStatus(exception);
        return ResponseEntity.status(status).body(
                new ErrorMessage()
                        .timestamp(System.currentTimeMillis())
                        .status(status.value())
                        .type(exception.getClass().getSimpleName())
                        .errorMessage(exception.getMessage())
        );
    }

    private HttpStatus resolveAnnotatedResponseStatus(Exception exception) {
        ResponseStatus annotation = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
