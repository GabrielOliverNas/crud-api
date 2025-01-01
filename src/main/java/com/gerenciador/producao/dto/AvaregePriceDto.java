package com.gerenciador.producao.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AvaregePriceDto {
    private String mediaPrecos;
}
