package br.com.project.meetime.exception;


import br.com.project.meetime.constant.ObservabilityTagConstant;
import br.com.project.meetime.enums.ActionEnum;
import br.com.project.meetime.util.LogUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Log4j2
@SuppressWarnings("serial")
public class ServiceException extends ApiException {

	public ServiceException(
			final String pageEnum,
			final ActionEnum actionEnum,
			final String messageError,
			final HttpStatusCode httpStatusCode,
			final Throwable e) {
		
        super(messageError, httpStatusCode);
    	log.error(LogUtil.buildMessage(messageError)
    			.with(ObservabilityTagConstant.Info.PAGE, pageEnum)
    			.with(ObservabilityTagConstant.Info.ACTION, actionEnum)
    			.with(ObservabilityTagConstant.Audit.HTTP_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR), e);
	}
}