package com.scarlet.venda.controller;

import com.scarlet.venda.client.EstoqueClient;
import com.scarlet.venda.client.responses.ProdutoResponse;
import com.scarlet.venda.model.beans.Venda;
import com.scarlet.venda.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping()
    public ResponseEntity<Venda> postVenda(@RequestBody ProdutoResponse entrada) {
        System.out.println(entrada);
        return ResponseEntity.ok().body(vendaService.save(entrada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVenda(@PathVariable int id){
        return ResponseEntity.ok().body(vendaService.getVenda(id));
    }

    public ResponseEntity<List<Venda>> getVendas(){
        return ResponseEntity.ok().body(vendaService.getVendas());
    }

}
