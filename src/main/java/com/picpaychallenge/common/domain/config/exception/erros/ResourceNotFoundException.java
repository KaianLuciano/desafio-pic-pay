package com.picpaychallenge.common.domain.config.exception.erros;

import com.picpaychallenge.common.domain.config.exception.messageerror.MessageError;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final MessageError messageError;

    public ResourceNotFoundException(MessageError messageError) {
        this.messageError = messageError;
    }
}
