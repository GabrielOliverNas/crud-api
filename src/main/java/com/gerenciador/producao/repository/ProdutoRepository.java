package com.gerenciador.producao.repository;

import com.gerenciador.producao.dto.AvaregePriceDto;
import com.gerenciador.producao.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    @Query(value = "SELECT * FROM PRODUTO_ENTITY " +
            "WHERE VALOR_SEM_DESCONTO = (SELECT MAX (VALOR_SEM_DESCONTO) FROM PRODUTO_ENTITY)", nativeQuery = true)
    ProdutoEntity byPrice();

    @Query(value = "SELECT AVG(VALOR_SEM_DESCONTO) FROM PRODUTO_ENTITY", nativeQuery = true)
    Object[] avaregePrice();
}
