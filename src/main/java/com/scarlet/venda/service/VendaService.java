package com.scarlet.venda.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarlet.venda.client.EstoqueClient;
import com.scarlet.venda.model.beans.*;
import com.scarlet.venda.model.enums.TipoPagamento;
import com.scarlet.venda.model.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
                item.setValor(a.get("valor").asDouble());
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

    public List<ResumoVenda> getVendaSemana() {
        List<ResumoVenda> resumoVendaList = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            var vendas = vendaRepository.findByDate(LocalDate.now().minusDays(i));
            resumoVendaList.add(getResumoVenda(vendas, i));
        }
        return resumoVendaList;
    }

    private ResumoVenda getResumoVenda(List<Venda> vendas, int daysToSubtract) {
        ResumoVenda resumoVenda = new ResumoVenda();

        resumoVenda.setData(LocalDate.now().minusDays(daysToSubtract));

        resumoVenda.setTotal(vendas.stream().reduce(0.0, (a, b) -> a + b.getTotal(), Double::sum));

        resumoVenda.setQuantidadeVendas(vendas.size());

        resumoVenda.setCartao(vendas.stream()
                .filter(a -> a.getFormaPagamento().equals(TipoPagamento.CARTAO))
                .mapToDouble(Venda::getTotal).sum());

        resumoVenda.setDinheiro(vendas.stream()
                .filter(a -> a.getFormaPagamento().equals(TipoPagamento.DINHEIRO))
                .mapToDouble(Venda::getTotal).sum());

        resumoVenda.setPix(vendas.stream()
                .filter(a -> a.getFormaPagamento().equals(TipoPagamento.PIX))
                .mapToDouble(Venda::getTotal).sum());

        return resumoVenda;
    }

    public ResumoVenda getVendaDia(LocalDate data) {
        var vendas = vendaRepository.findByDate(data);
        return getResumoVenda(vendas, 0);
    }

    public List<VendaMaisItemModificado> getVendasDia(LocalDate data) {
        var vendas = vendaRepository.findByDate(data);
        return vendas.stream().map(venda -> {
            List<ItemMaisNome> lista = new ArrayList<>();
            venda.getItens().forEach(item -> {
                lista.add(new ItemMaisNome(item, estoqueClient.getNomeProduto(item.getProdutoId())));
            });
            return new VendaMaisItemModificado(venda, lista);
        }).toList();

    }

    public List<Produto> getVendaPorCategorias(String data1, String data2) {
        LocalDate dataInicial = null;
        LocalDate dataFinal = null;

        List<String> x;

        // Se somente um for null então quer dizer que é pretendido apenas um dia

        if (data1 == null && data2 == null) {
            // Se ambos forem null então quer dizer que não foi passado datas, então pega a semana atual
            dataInicial = LocalDate.now().minusDays(7);
            dataFinal = LocalDate.now();
            x = vendaRepository.findAllByDataHoraBetween(dataInicial, dataFinal);
        } else if (data1 == null ^ data2 == null) {
            dataInicial = data1 != null ? LocalDate.parse(data1, DateTimeFormatter.ofPattern("dd-MM-yyyy")) : LocalDate.parse(data2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            x = vendaRepository.findAllByDataHoraBetween(dataInicial);
        } else {
            // Se ambos não forem null então quer dizer que foi passado datas, então pega as datas passadas por parâmetro
            dataInicial = LocalDate.parse(data1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            dataFinal = LocalDate.parse(data2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            x = vendaRepository.findAllByDataHoraBetween(dataInicial, dataFinal);
        }


        HashMap<Integer, Produto> list = new HashMap<>();
        for (String s : x) {
            String[] split = s.split(",");
            int idProduto = Integer.parseInt(split[0]);
            int quantidade = Integer.parseInt(split[1]);
            String tamanho = split[2];
            if (list.containsKey(idProduto)) {
                Produto produto = list.get(idProduto);
                produto.setQuantidade(produto.getQuantidade() + quantidade);
                for (int i = 0; i < quantidade; i++) {
                    produto.setTamanho(produto.getTamanho() + "," + tamanho);
                }
                list.put(idProduto, produto);
            } else {
                list.put(idProduto, new Produto(idProduto, quantidade, getTamanho(quantidade, tamanho)));
            }
        }
        var p = estoqueClient.getTamanhos(list.keySet().stream().toList());
        for (String s : p) {
            String[] split = s.split(",");
            int idProduto = Integer.parseInt(split[0]);
            String categorias = split[1];
            Produto produto = list.get(idProduto);
            produto.setCategorias(Arrays.stream(categorias.split(";")).toList());
            list.put(idProduto, produto);
        }
        return list.values().stream().toList();
    }

    private String getTamanho(int quantidade, String tamanho) {
        StringBuilder sb = new StringBuilder();
        if (quantidade == 1) {
            sb.append(tamanho);
        } else {
            for (int i = 0; i < quantidade; i++) {
                sb.append(tamanho).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
