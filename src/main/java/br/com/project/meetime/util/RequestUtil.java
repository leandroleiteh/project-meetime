package br.com.project.meetime.util;

import br.com.project.rest.v1.model.Error;
import br.com.project.rest.v1.model.ErrorInner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@RequiredArgsConstructor
public class RequestUtil {

    public Error buildError(
            final HttpStatusCode httpStatusCode,
            final String messageError,
            final Object... messageArguments) {

        Error error = new Error();
        ErrorInner errorInner = new ErrorInner();
        errorInner.setCode(httpStatusCode.toString());
        errorInner.setMessage(MessageFormat.format(messageError, messageArguments));
        error.add(errorInner);

        return error;
    }
}
