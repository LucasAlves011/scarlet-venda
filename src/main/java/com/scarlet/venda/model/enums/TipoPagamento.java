package com.scarlet.venda.model.enums;

import lombok.Getter;

@Getter
public enum TipoPagamento {

    PIX("PIX"),CARTAO("CARTAO"),DINHEIRO("DINHEIRO");

    private final String descricao;

    TipoPagamento(String descricao) {
        this.descricao = descricao;
    }
}
