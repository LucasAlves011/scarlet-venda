package com.scarlet.venda.model.beans;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity(name = "item")
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "produto_id")
    private int produtoId;
    private int quantidade;

    @Length(max = 20)
    private String tamanho;
}
