package br.com.project.meetime.logging;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.StringMapMessage;
import org.owasp.encoder.Encode;


import java.util.Objects;

@NoArgsConstructor
public class BuilderLogMessage extends StringMapMessage {


    public static BuilderLogMessage message(String description) {
        return new BuilderLogMessage().description(description);
    }

    public static BuilderLogMessage message(String className, String methodName) {
        return new BuilderLogMessage().className(className).methodName(methodName);
    }

    @Override
    public BuilderLogMessage with(String key, Object value) {
        super.with(key, Objects.nonNull(value) ? Encode.forJava(value.toString()) : "");
        return this;
    }

    private BuilderLogMessage description(String description) {
        return (BuilderLogMessage) with("message", description);
    }

    private BuilderLogMessage methodName(String methodName) {
        return (BuilderLogMessage) with("log.origin.function", methodName);
    }

    private BuilderLogMessage className(String className) {
        return (BuilderLogMessage) with("log.origin.class", className);
    }
}