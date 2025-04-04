package br.com.project.meetime.exception;


import br.com.project.meetime.constant.ObservabilityTagConstant;
import br.com.project.meetime.util.LogUtil;
import br.com.project.meetime.util.Util;
import br.com.project.rest.v1.model.Error;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Log4j2
@SuppressWarnings("serial")
public class ApiIntegrationException extends ApiException {

    public ApiIntegrationException(
            final String endpoint,
            final int totalApiRetryAttempts,
            final long totalExecutionTime,
            final Error error,
            final String requestBody,
            final HttpStatusCode httpStatusCode,
            final String messageError,
            final Throwable e) {

        super(messageError, httpStatusCode);
        log.error(LogUtil.buildMessage(messageError)
                .with(ObservabilityTagConstant.Info.INTEGRATION_ENDPOINT, endpoint)
                .with(ObservabilityTagConstant.Info.REQUEST_RETRY, totalApiRetryAttempts)
                .with(ObservabilityTagConstant.Info.RESPONSE_TIME, totalExecutionTime)
                .with(ObservabilityTagConstant.Info.REQUEST_BODY, Util.encodeBase64(requestBody))
                .with(ObservabilityTagConstant.Info.RESPONSE_BODY, Util.encodeBase64(ArrayUtils.toString(error)))
                .with(ObservabilityTagConstant.Audit.HTTP_STATUS_CODE, httpStatusCode), e);
    }
}