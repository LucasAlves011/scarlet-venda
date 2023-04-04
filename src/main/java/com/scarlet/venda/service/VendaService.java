package com.scarlet.venda.service;


import com.scarlet.venda.client.EstoqueClient;
import com.scarlet.venda.client.responses.ProdutoResponse;
import com.scarlet.venda.model.beans.Venda;
import com.scarlet.venda.model.enums.TipoPagamento;
import com.scarlet.venda.model.repository.VendaRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final EstoqueClient estoqueClient;

    public VendaService(VendaRepository vendaRepository, EstoqueClient estoqueClient) {
        this.vendaRepository = vendaRepository;
        this.estoqueClient = estoqueClient;
    }

    public Venda save(ProdutoResponse entrada) {
       boolean resposta = estoqueClient.verificarProdutos(entrada.getItens());

       if (!resposta){
           throw new RuntimeException("Não há estoque suficiente para a venda");
       }
       var venda = new Venda();
        venda.setTotal(entrada.getValor());
        venda.setDesconto(entrada.getDesconto());
        venda.setEntrega(entrada.getEntrega());
        venda.setFormaPagamento(TipoPagamento.valueOf(entrada.getFormaPagamento()));
        venda.setItens(entrada.getItens());

        return vendaRepository.save(venda);
    }

    public Venda getVenda(int id){
        return vendaRepository.findById(id).get();
    }

    public List<Venda> getVendas(){
        return vendaRepository.findAll();
    }

}
