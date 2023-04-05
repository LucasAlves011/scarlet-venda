package com.scarlet.venda.controller;

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

    @PostMapping("/cadastro")
    public ResponseEntity<Venda> adsa(@RequestParam String venda) {
        System.out.println(venda);
        return ResponseEntity.ok().body(vendaService.save(venda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVenda(@PathVariable int id) {
        return ResponseEntity.ok().body(vendaService.getVenda(id));
    }

    @GetMapping()
    public ResponseEntity<List<Venda>> getVendas() {
        return ResponseEntity.ok().body(vendaService.getVendas());
    }

    @PostMapping("/verificar")
    public ResponseEntity<String> teste(@RequestBody String string) {
        return ResponseEntity.ok().body(string);
    }
}
