package com.scarlet.venda.model.repository;

import com.scarlet.venda.model.beans.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
