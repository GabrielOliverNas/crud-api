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

   /* public ProdutoEntity save(ProdutoEntity entity) {
        return repository.save(entity);
    }*/

    public List<ProdutoEntity> list() {
        return calculateDiscount(repository.findAll());
    }

    public ProdutoEntity save(ProdutoEntity produto) {

        if (!Objects.isNull(produto.getId())) {

            ProdutoEntity byId = repository.findById(produto.getId()).get();

            byId.setNome(produto.getNome());
            byId.setValorSemDesconto(produto.getValorSemDesconto());
            byId.setTipoProduto(produto.getTipoProduto());
            byId.setPesoKg(produto.getPesoKg());
            byId.setTamanhoMb(produto.getTamanhoMb());
            byId.setValorFrete(produto.getValorFrete());
            byId.setPromocao(produto.getPromocao());
            byId.setValorComDesconto(produto.getValorComDesconto());
            byId.setValorTotalComFrete(produto.getValorTotalComFrete());

            repository.save(byId);
        } else {
            repository.save(produto);
        }

        return produto;
    }

    public void delete(ProdutoEntity produto) {
        repository.deleteById(produto.getId());
    }

    // Pensei na solucao assim afim de reduzir codigos duplicados
    public ProdutoEntity byPrice() {
        return calculateDiscount(List.of(repository.byPrice())).get(0);
    }

    public AvaregePriceDto avaregePrice() {
        DecimalFormat df = new DecimalFormat("#,##0.00");

        final Object[] objects = repository.avaregePrice();

        AvaregePriceDto avaregePriceDto = new AvaregePriceDto();
        avaregePriceDto.setMediaPrecos(df.format(objects[0]));
        return avaregePriceDto;
    }

    private List<ProdutoEntity> calculateDiscount(List<ProdutoEntity> produtosBd) {
        return produtosBd.stream().peek(item -> {
            // promocao
            if (item.getPromocao()) {

                BigDecimal desconto = DESCONTO.multiply(item.getValorSemDesconto())
                        .divide(valueOf(100));

                item.setValorComDesconto(item.getValorSemDesconto().subtract(desconto));
            }
            // elegivel a frete
            if (TipoProdutoEnum.FISICO.name().equals(item.getTipoProduto())) {
                item.setValorFrete(item.getPesoKg().multiply(PESO_KG));
            }
            // calculo valor total em promocao
            if (TipoProdutoEnum.FISICO.name().equals(item.getTipoProduto()) &&
                    item.getPromocao()) {
                item.setValorTotalComFrete(item.getValorFrete().add(item.getValorComDesconto()));
            }
        }).toList();
    }
}
