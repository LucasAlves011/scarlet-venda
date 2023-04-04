package com.scarlet.venda.model.beans;

import com.scarlet.venda.model.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(updatable = false)
    private LocalDateTime data_hora;

    private double total;
    private double desconto;
    private double entrega;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo")
    private TipoPagamento tipoPagamento;

//    @JoinTable(name = "venda_itens",joinColumns = @JoinColumn(name = "venda_id")
//            ,inverseJoinColumns = @JoinColumn(name = "item_id"))
//    private List<Item> itens;

}
