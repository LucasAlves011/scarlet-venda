package com.scarlet.venda.model.beans;


import com.scarlet.venda.model.enums.TipoPagamento;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VendaMaisItemModificado {
    private double total;
    private double desconto;
    private double entrega;
    private LocalDateTime dataHora;
    private TipoPagamento formaPagamento;
    private List<ItemMaisNome> itemMaisNomeList;

    public VendaMaisItemModificado(Venda venda, List<ItemMaisNome> itemMaisNomeList) {
        this.total = venda.getTotal();
        this.desconto = venda.getDesconto();
        this.entrega = venda.getEntrega();
        this.dataHora = venda.getDataHora();
        this.formaPagamento = venda.getFormaPagamento();
        this.itemMaisNomeList = itemMaisNomeList;
    }


}
