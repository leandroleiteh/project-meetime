package br.com.project.meetime.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Base64;

@Log4j2
@Component
@RequiredArgsConstructor
public class Util {

    private final ObjectWriter objectWriter;
    private final ObjectMapper objectMapper;


    public static int toByte(
            final int value) {

        return value * 1024 * 1024;
    }

    public static String encodeBase64(
            final String value) {

        String encodedValue = StringUtils.EMPTY;

        if (ObjectUtils.isNotEmpty(value)) {

            encodedValue = new String(Base64.getEncoder()
                    .encode(value.getBytes(Charset.defaultCharset())), Charset.defaultCharset());
        }

        return encodedValue;
    }

    public static String toString(
            final String value) {

        String newValue = StringUtils.EMPTY;

        if (ObjectUtils.isNotEmpty(value)) {
            newValue = value;
        }

        return newValue;
    }

    public String parseToJson(
            final Object object) {

        String json = null;

        try {

            if (object != null) {
                json = objectWriter.writeValueAsString(object);
            }

        } catch (Exception e) {
            log.error("Ocorreu um erro ao realizar o parse do objeto para json.", e);
        }

        return json;
    }

    public <T> T parseJsonToType(
            final String json,
            final Class<T> clazz) {

        T objectType = null;

        try {

            if (json != null) {
                objectType = objectMapper.readValue(json, clazz);
            }

        } catch (Exception e) {
            log.error("Ocorreu um erro ao realizar parse de um json para um objeto.", e);
        }

        return objectType;
    }
}