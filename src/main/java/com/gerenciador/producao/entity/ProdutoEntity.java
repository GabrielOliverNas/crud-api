package com.gerenciador.producao.entity;

import com.gerenciador.producao.enums.TipoProdutoEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@Entity
@Getter
@Setter
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @NonNull
    private Long preco;
    @NonNull
    private TipoProdutoEnum tipoProduto;

    @NonNull
    private Long pesoKg;
    @NonNull
    private Long pesoMB;

    private Long valorFrete;

    @NonNull
    private Boolean promocao;

    private Long valorComDesconto;
    private Long valorComFrete;
}