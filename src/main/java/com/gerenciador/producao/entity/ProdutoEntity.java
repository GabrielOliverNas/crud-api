package com.gerenciador.producao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;


@Entity
@Getter
@Setter
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "'nome' nao pode ser nulo")
    @Size(min = 0)
    private String nome;

    @NotNull(message = "'valorSemDesconto' nao pode ser nulo")
    @Min(0)
    private BigDecimal valorSemDesconto;

    @NotNull(message = "'tipoProduto' nao pode ser nulo")
    @NotNull
    private String tipoProduto;

    @NotNull
    private Boolean promocao;

    private BigDecimal pesoKg;
    private BigDecimal tamanhoMb;
    private BigDecimal valorFrete;
    private BigDecimal valorComDesconto;
    private BigDecimal valorTotalComFrete;
}
