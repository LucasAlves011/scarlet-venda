package com.scarlet.venda.model.beans;

import com.scarlet.venda.model.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@Entity(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(updatable = false)
    private LocalDateTime data_hora = LocalDateTime.now();

    private double total;
    private double desconto;
    private double entrega;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo_pagamento")
    private TipoPagamento formaPagamento;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Item> itens;
}
