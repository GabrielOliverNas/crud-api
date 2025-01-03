package com.gerenciador.producao.service;

import com.gerenciador.producao.dto.AvaregePriceDto;
import com.gerenciador.producao.entity.ProdutoEntity;
import com.gerenciador.producao.enums.TipoProdutoEnum;
import com.gerenciador.producao.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.valueOf;

@Service
public class ProdutoService {

    private final BigDecimal DESCONTO = valueOf(10);
    private final BigDecimal PESO_KG = valueOf(10);

    @Autowired
    private ProdutoRepository repository;

    public ProdutoEntity save(ProdutoEntity entity) {
        return repository.save(entity);
    }

    public List<ProdutoEntity> list() {
        return repository.findAll()
                .stream()
                .map(this::calculateDiscount)
                .toList();
    }

    // codigo foi pensado assim pois valores como
    // frete, promocao entre outros sao valores mutaveis
    // e necessitam refazer os calculos
    public ProdutoEntity alterar(ProdutoEntity produto) {

        ProdutoEntity produtoEntity = new ProdutoEntity();

        if (!Objects.isNull(produto.getId())) {

            ProdutoEntity byId = repository.findById(produto.getId()).get();

            byId.setNome(produto.getNome());
            byId.setValorSemDesconto(produto.getValorSemDesconto());
            byId.setTipoProduto(produto.getTipoProduto());
            byId.setPesoKg(produto.getPesoKg());
            byId.setTamanhoMb(produto.getTamanhoMb());
            byId.setPromocao(produto.getPromocao());

            repository.save(byId);

            produtoEntity = calculateDiscount(byId);
        }

        return produtoEntity;
    }

    public void delete(ProdutoEntity produto) {
        repository.deleteById(produto.getId());
    }

    public ProdutoEntity byPrice() {
        return calculateDiscount(repository.byPrice());
    }

    public AvaregePriceDto avaregePrice() {
        DecimalFormat df = new DecimalFormat("#,##0.00");

        final Object[] objects = repository.avaregePrice();

        AvaregePriceDto avaregePriceDto = new AvaregePriceDto();
        avaregePriceDto.setMediaPrecos(df.format(objects[0]));
        return avaregePriceDto;
    }

    // codigo de calculos centralizados para reduzir duplicidade
    private ProdutoEntity calculateDiscount(ProdutoEntity produtosBd) {

        // produto promocional
        if (produtosBd.getPromocao()) {
            BigDecimal desconto = DESCONTO.multiply(produtosBd.getValorSemDesconto())
                    .divide(valueOf(100));
            produtosBd.setValorComDesconto(
                    produtosBd.getValorSemDesconto().subtract(desconto));
        }
        // elegivel a frete
        if (TipoProdutoEnum.FISICO.name().equals(produtosBd.getTipoProduto())) {
            produtosBd.setValorFrete(produtosBd.getPesoKg().multiply(PESO_KG));

            if (!produtosBd.getPromocao()) {
                produtosBd.setValorTotalComFrete(
                        produtosBd.getValorFrete().add(produtosBd.getValorSemDesconto()));
            } else {
                produtosBd.setValorTotalComFrete(
                        produtosBd.getValorFrete().add(produtosBd.getValorComDesconto()));
            }

        }

        return produtosBd;
    }
}
