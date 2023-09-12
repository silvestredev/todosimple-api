package dev.silvestredev.todosimple.models.enums;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProfileEnum {
    
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private Integer code;
    private String description;


    public static ProfileEnum toEnum(Integer code) {

        if (Objects.isNull(code)) {
            return null;
        }

        //percorrendo os valores possíveis do ProfileEnum e retornando o que for igual
        for (ProfileEnum x : ProfileEnum.values()) {
            if (code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid code: " + code); // caso o usuário passe um valor diferente de 1 e 2
    }
}
