package br.com.project.meetime.exception;

import br.com.project.rest.v1.model.Error;
import br.com.project.rest.v1.model.ErrorInner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Error> handleApiException(ApiException ex) {
        Error errorResponse = new Error();
        ErrorInner errorInner = new ErrorInner();

        errorInner.setCode(ex.getHttpStatusCode().toString());
        errorInner.setMessage(ex.getMessage());

        errorResponse.add(errorInner);

        return ResponseEntity.status(ex.getHttpStatusCode()).body(errorResponse);
    }
}
