package com.scarlet.venda.model.repository;

import com.scarlet.venda.model.beans.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {
    @Query(value = "SELECT * FROM venda v WHERE date_trunc('day', v.data_hora) = :data", nativeQuery = true)
    List<Venda> findByDate(LocalDate data);
}
