package com.scarlet.venda.controller;

import com.scarlet.venda.model.beans.Produto;
import com.scarlet.venda.model.beans.ResumoVenda;
import com.scarlet.venda.model.beans.Venda;
import com.scarlet.venda.model.beans.VendaMaisItemModificado;
import com.scarlet.venda.service.VendaService;
import jakarta.ws.rs.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @GetMapping("/resumo-semana")
    public ResponseEntity<List<ResumoVenda>> getVendaSemana() {
        return ResponseEntity.ok().body(vendaService.getVendaSemana());
    }

    @GetMapping("/venda-por-categorias")
    public ResponseEntity<List<Produto>> getVendaPorCategorias(@PathParam("data1") String data1, @PathParam("data2") String data2) {
        return ResponseEntity.ok().body(vendaService.getVendaPorCategorias(data1, data2));
    }

    @GetMapping("/resumo-dia")
    public ResponseEntity<ResumoVenda> getVendaDia(@PathParam("data") String data) {
        return ResponseEntity.ok().body(vendaService.getVendaDia(LocalDate.parse(data, DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
    }

    @GetMapping("/vendas-dia")
    public ResponseEntity<List<VendaMaisItemModificado>> getVendasDia(@PathParam("data") String data) {
        return ResponseEntity.ok().body(vendaService.getVendasDia(LocalDate.parse(data, DateTimeFormatter
                .ofPattern("dd-MM-yyyy"))));
    }


}
