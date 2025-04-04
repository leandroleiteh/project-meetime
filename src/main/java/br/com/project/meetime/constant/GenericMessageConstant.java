package br.com.project.meetime.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GenericMessageConstant {

	@UtilityClass
	public class Info {

		public static final String SUCCESS_REQUEST =
				"Sucesso ao processar a solicitação: {}";
	}
	
	@UtilityClass
	public class Error {

		public static final String NOT_SAVED =
				"Ocorreu um problema, não foi possível salvar os dados, tente novamente mais tarde.";

		public static final String INTEGRATION_API =
				"Ocorreu um problema, não possível processar a solicitação, tente novamente mais tarde.";

		public static final String EXCHANGING_CODE =
				"Ocorreu um problema, não possível trocar o código, tente novamente mais tarde.";
	}
}
