package com.picpaychallenge.common.domain.config.exception.messageerror;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

@Getter
@JsonSerialize(using = MessageErrorSerializer.class)
public enum MessageError {

    USER_NOT_FOUND(404, "USER_NOT_FOUND", "USER not found");

    private final int status;
    private final String code;
    private final String message;

    MessageError(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
