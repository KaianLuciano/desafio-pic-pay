package com.picpaychallenge.common.domain.config.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(hidden = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {
    private Long timeStamp;
    private Integer status;
    private String error;
    private Object message;
    private String path;
}