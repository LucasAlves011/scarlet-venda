package com.scarlet.venda.model.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResumoVenda {

    double total;
    int quantidadeVendas;
    double cartao;
    double dinheiro;
    double pix;
    LocalDate data;

}
