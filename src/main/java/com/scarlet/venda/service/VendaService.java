package com.scarlet.venda.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarlet.venda.client.EstoqueClient;
import com.scarlet.venda.model.beans.Item;
import com.scarlet.venda.model.beans.Venda;
import com.scarlet.venda.model.enums.TipoPagamento;
import com.scarlet.venda.model.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final EstoqueClient estoqueClient;

    public VendaService(VendaRepository vendaRepository, EstoqueClient estoqueClient) {
        this.vendaRepository = vendaRepository;
        this.estoqueClient = estoqueClient;
    }

    private Venda desestructry(String string) {
        Venda venda;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(string);
            venda = new Venda();
            venda.setTotal(jsonNode.get("total").asDouble());
            venda.setDesconto(jsonNode.get("desconto").asDouble());
            venda.setEntrega(jsonNode.get("entrega").asDouble());
            venda.setFormaPagamento(TipoPagamento.valueOf(jsonNode.get("formaPagamento").asText()));
            List<Item> arrayitens = new ArrayList<>();
            var itens = jsonNode.get("itens");
            itens.forEach(a -> {
                Item item = new Item();
                item.setProdutoId(a.get("produtoId").asInt());
                item.setQuantidade(a.get("quantidade").asInt());
                item.setTamanho(a.get("tamanho").asText());
                arrayitens.add(item);
            });
            venda.setItens(arrayitens);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return venda;
    }

    public Venda save(String string) {

        Venda entrada = desestructry(string);

        boolean resposta = estoqueClient.verificarProdutos(entrada.getItens());

        if (!resposta) {
            throw new RuntimeException("Não há estoque suficiente para a venda");
        }
        var venda = new Venda();
        venda.setTotal(entrada.getTotal());
        venda.setDesconto(entrada.getDesconto());
        venda.setEntrega(entrada.getEntrega());
        venda.setFormaPagamento(entrada.getFormaPagamento());
        venda.setItens(entrada.getItens());

        return vendaRepository.save(venda);
    }

    public Venda getVenda(int id) {
        return vendaRepository.findById(id).get();
    }

    public List<Venda> getVendas() {
        return vendaRepository.findAll();
    }

}
