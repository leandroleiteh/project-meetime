package br.com.project.meetime.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@SuppressWarnings("serial")
public class ApiException extends RuntimeException {
	
	private final String message;
	private final HttpStatusCode httpStatusCode;
    
    public ApiException(
    		final String message, 
    		final HttpStatusCode httpStatusCode) {
    	
        super();
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
