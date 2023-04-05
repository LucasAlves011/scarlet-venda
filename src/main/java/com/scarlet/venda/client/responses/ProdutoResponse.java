package com.scarlet.venda.client.responses;

import com.scarlet.venda.model.beans.Item;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProdutoResponse {

    private double total;
    private double entrega;
    private double desconto;
    private List<Item> itens;
    private String formaPagamento;
}
