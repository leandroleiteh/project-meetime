package br.com.project.meetime.mapper;

import br.com.project.rest.v1.model.AccessTokenHubspotResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface AccessTokenHubspotMapper {

    AccessTokenHubspotMapper INSTANCE = Mappers.getMapper(AccessTokenHubspotMapper.class);

    @Mapping(source = "access_token", target = "accessToken", qualifiedByName = "mapToString")
    @Mapping(source = "token_type", target = "tokenType", qualifiedByName = "mapToString")
    @Mapping(source = "expires_in", target = "expiresIn", qualifiedByName = "mapToInteger")
    AccessTokenHubspotResponse toResponse(Map<String, Object> response);

    @Named("mapToString")
    default String mapToString(final Object value) {
        return value != null ? value.toString() : null;
    }

    @Named("mapToInteger")
    default Integer mapToInteger(final Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        try {
            return value != null ? Integer.parseInt(value.toString()) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
