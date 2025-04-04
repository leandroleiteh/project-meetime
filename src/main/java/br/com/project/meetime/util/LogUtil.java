package br.com.project.meetime.util;

import br.com.project.meetime.logging.BuilderLogMessage;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;

@Log4j2
@UtilityClass
public class LogUtil {

    public static BuilderLogMessage buildMessage(
    		final String message, 
    		final Object... values) {

        BuilderLogMessage builderStringMessage = null;
        
        try {
        	
            if (StringUtils.isNotEmpty(message)) {

                if (CollectionUtils.isNotEmpty(Arrays.asList(values))) {
                    builderStringMessage = BuilderLogMessage
                            .message(MessageFormat.format(StringUtils.stripAccents(message), values));
                } else {
                    builderStringMessage = BuilderLogMessage
                            .message(StringUtils.stripAccents(message));
                }
            }
            
        } catch (Exception e) {

            log.error(BuilderLogMessage.message(
                    "<< [ERROR][LOG-BUILD-MESSAGE]: "
                            + "Ocorreu um erro ao criar uma mensagem para [Observability]. Values: {0}",
                    ArrayUtils.toString(values)), e);
        }
        
        return builderStringMessage;
    }
}