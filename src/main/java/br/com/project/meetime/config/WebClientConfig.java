package br.com.project.meetime.config;

import br.com.project.meetime.util.Util;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.Objects;

@Log4j2
@Configuration
public class WebClientConfig {

    private final static String LOCAL = "local";

    @Value("${webclient.api.payload.size}")
    private int apiPayloadSize;

    @Value("${webclient.api.environment}")
    private String environment;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {

        WebClient webclient;

        try {

            ClientHttpConnector connector = new ReactorClientHttpConnector(buildHttpClient());

            webclient = builder
                    .clientConnector(connector)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .exchangeStrategies(ExchangeStrategies.builder().codecs(codecs ->
                            codecs.defaultCodecs().maxInMemorySize(Util.toByte(apiPayloadSize))).build())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("WebClient", e);
        }

        return webclient;
    }

    private HttpClient buildHttpClient() throws Exception {

        HttpClient httpClient = null;

        if (Objects.isNull(environment)) {

            httpClient = HttpClient.create()
                    .resolver(DefaultAddressResolverGroup.INSTANCE)
                    .wiretap(true);
        }

        if( Objects.nonNull(environment) && Objects.equals(environment, LOCAL)) {

            log.info("[HTTP CLIENT][Local] Iniciando sem SSL ...");
            SslContext sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();

            httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext))
                    .resolver(DefaultAddressResolverGroup.INSTANCE)
                    .wiretap(true);

        }

        return httpClient;
    }
}