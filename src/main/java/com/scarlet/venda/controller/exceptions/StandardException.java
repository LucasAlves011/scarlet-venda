package com.scarlet.venda.controller.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StandardException {

    private LocalDateTime dataHoraUTC;
    private Integer status;
    private String message;

    public StandardException(LocalDateTime data, Integer status, String message) {
        this.dataHoraUTC = data;
        this.status = status;
        this.message = message;
    }

    public StandardException() {
        super();
    }
}