package com.scarlet.venda.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/venda")
public class VendaController {

    @GetMapping("")
    public String teste() {
        return "Resposta da venda";
    }

    @PostMapping()
    public void testePost() {
        System.out.println("Post da venda");
    }

//    @GetMapping("/chamada")
//    public void chamada() {
//        System.out.println("Chamada do estoque");
//    }

}
