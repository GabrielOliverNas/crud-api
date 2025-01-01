package com.gerenciador.producao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(min = 0)
    private String nome;

    @NonNull
    @Min(0)
    private BigDecimal valorSemDesconto;

    @NonNull
    private String tipoProduto;

    @NonNull
    private BigDecimal pesoKg;

    @NonNull
    private BigDecimal tamanhoMb;

    private BigDecimal valorFrete;

    @NonNull
    private Boolean promocao;

    private BigDecimal valorComDesconto;
    private BigDecimal valorTotalComFrete;
}
