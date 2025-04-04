package br.com.project.meetime.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusRequestEnum implements AbstractEnum {

    SUCCESS("SUCESSO"),
    ERROR("ERRO");

    private String ptValue;

    @Override
    public String getValueToCompare() {
        return this.name();
    }

    @Override
    public String getAllowValue() {
        return this.name();
    }
}
