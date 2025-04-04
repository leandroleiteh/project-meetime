package br.com.project.meetime.constant;

public class ObservabilityTagConstant {

	private static final String PREFIX = "custom.project.meetime.api.";


	public class Audit {

		public static final String HTTP_STATUS_CODE = PREFIX + "audit.http.status.code";
	}

	public class Info {

		public static final String PAGE = PREFIX + "info.page";
		public static final String ACTION =  PREFIX + "info.action";

		public static final String STATUS_REQUEST = PREFIX + "info.status.request";
		public static final String RESPONSE_TIME = PREFIX + "info.response.time";
		public static final String REQUEST_BODY = PREFIX + "info.request.body";
		public static final String RESPONSE_BODY = PREFIX + "info.response.body";
		public static final String REQUEST_RETRY = PREFIX + "info.request.retry";
		public static final String INTEGRATION_ENDPOINT = PREFIX + "info.integration.endpoint";
	}
}
