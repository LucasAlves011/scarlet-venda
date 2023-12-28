package com.scarlet.venda.client;

import com.scarlet.venda.client.responses.ProdutoResponse;
import com.scarlet.venda.model.beans.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "estoque", url = "http://localhost:9091/estoque")
public interface EstoqueClient {

    @GetMapping("/produto/{id}")
    ProdutoResponse getProduto(@PathVariable int id);

    @GetMapping("/produto")
    List<ProdutoResponse> getProdutos();

    @PostMapping("/io")
    boolean verificarProdutos(@RequestBody List<Item> itens);

    @GetMapping("/produto/nome/{id}")
    String getNomeProduto(@PathVariable int id);

    @PostMapping("/produto/tamanhos")
    List<String> getTamanhos(@RequestBody List<Integer> ids);

}
