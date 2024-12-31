package com.gerenciador.producao.service;

import com.gerenciador.producao.entity.ProdutoEntity;
import com.gerenciador.producao.enums.TipoProdutoEnum;
import com.gerenciador.producao.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProdutoService {

    private final Long DESCONTO = 10L;
    private final Long PESO_KG = 10L;

    @Autowired
    private ProdutoRepository repository;

    public ProdutoEntity save(ProdutoEntity entity) {
        return repository.save(entity);
    }

    public List<ProdutoEntity> list() {

        final List<ProdutoEntity> produtosBd = repository.findAll();

        List<ProdutoEntity> produtoList = produtosBd.stream().peek(item -> {
            // promocao
            if (item.getPromocao()) {
                Long desconto = DESCONTO * item.getValorSemDesconto() / 100;
                item.setValorComDesconto(item.getValorSemDesconto() - desconto);
            }
            // elegivel a frete
            if (TipoProdutoEnum.FISICO.name().equals(item.getTipoProduto().name())) {
                item.setValorFrete(item.getPesoKg() * PESO_KG);
            }
            // calculo valor total em promocao
            if (TipoProdutoEnum.FISICO.name().equals(item.getTipoProduto().name()) &&
                    item.getPromocao()) {
                item.setValorTotalComFrete(item.getValorFrete() + item.getValorComDesconto());
            }

        }).toList();

        return produtoList;
    }

    public ProdutoEntity put(ProdutoEntity produto) {

        ProdutoEntity byId = new ProdutoEntity();

        if (!Objects.isNull(produto.getId())) {
            byId = repository.findById(produto.getId()).get();
        }

        byId.setNome(produto.getNome());
        byId.setValorSemDesconto(produto.getValorSemDesconto());
        byId.setTipoProduto(produto.getTipoProduto());
        byId.setPesoKg(produto.getPesoKg());
        byId.setPesoMb(produto.getPesoMb());
        byId.setValorFrete(produto.getValorFrete());
        byId.setPromocao(produto.getPromocao());
        byId.setValorComDesconto(produto.getValorComDesconto());
        byId.setValorTotalComFrete(produto.getValorTotalComFrete());

        repository.save(byId);

        return byId;
    }

    public void delete(ProdutoEntity produto) {
        repository.deleteById(produto.getId());
    }
}
