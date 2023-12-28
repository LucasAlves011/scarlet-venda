package com.scarlet.venda.model.repository;

import com.scarlet.venda.model.beans.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {
    @Query(value = "SELECT * FROM venda v WHERE date_trunc('day', v.data_hora) = :data", nativeQuery = true)
    List<Venda> findByDate(LocalDate data);

    @Query(value = "select item.produto_id,item.quantidade,item.tamanho from venda_itens inner join item on venda_itens.itens_id = item.id inner join venda  on venda_itens.venda_id = venda.id where venda.data_hora between :data1 and :data2", nativeQuery = true)
    List<String> findAllByDataHoraBetween(LocalDate data1, LocalDate data2);

    @Query(value = "select item.produto_id,item.quantidade,item.tamanho from venda_itens inner join item on venda_itens.itens_id = item.id inner join venda  on venda_itens.venda_id = venda.id where date_trunc('day',venda.data_hora) = :data1", nativeQuery = true)
    List<String> findAllByDataHoraBetween(LocalDate data1);
}
