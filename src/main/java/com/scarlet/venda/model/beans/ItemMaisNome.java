package com.scarlet.venda.model.beans;

import lombok.Data;

@Data
public class ItemMaisNome {
    private Item item;
    private String nome;

    public ItemMaisNome(Item item, String nome) {
        this.item = item;
        this.nome = nome;
    }
}
