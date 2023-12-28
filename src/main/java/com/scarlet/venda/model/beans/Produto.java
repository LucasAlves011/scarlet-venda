package com.scarlet.venda.model.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Produto {

    private int idProduto;
    private int quantidade;
    private String tamanho;
    private List<String> categorias;

    public Produto(int idProduto, int quantidade, String tamanho) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.tamanho = tamanho;
    }
}
