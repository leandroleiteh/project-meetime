package br.com.project.meetime.util;


import br.com.project.meetime.constant.GenericMessageConstant;
import lombok.RequiredArgsConstructor;
import br.com.project.rest.v1.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.Charset;


@Component
@RequiredArgsConstructor
public class WebClientUtil {
	
	private final Util util;
	private final RequestUtil requestUtil;
	
	public static final Integer API_TIMEOUT = 30;
	public static final Integer API_RETRY_ATTEMPTS = 1;

	public Error buildError(
			final Throwable responseException) {

		var error = new Error();

		try {

			if(responseException instanceof WebClientResponseException) {

				var webClientResponseException =
						((WebClientResponseException) responseException);

				error.addAll(util.parseJsonToType(
						webClientResponseException.getResponseBodyAsString(
								Charset.defaultCharset()), Error.class));
			}else {

				requestUtil.buildError(HttpStatus.INTERNAL_SERVER_ERROR,
						GenericMessageConstant.Error.INTEGRATION_API);
			}

		} catch (Exception e) {

			requestUtil.buildError(HttpStatus.INTERNAL_SERVER_ERROR,
					GenericMessageConstant.Error.INTEGRATION_API);
		}

		return error;
	}

	public HttpStatusCode buildHattpStatusError(
			final Throwable responseException) {

		var httpStatusCode = HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

		if(responseException instanceof WebClientResponseException) {

			var webClientResponseException =
					((WebClientResponseException) responseException);

			httpStatusCode = HttpStatusCode.valueOf(webClientResponseException.getStatusCode().value());

		}

		return httpStatusCode;
	}
}