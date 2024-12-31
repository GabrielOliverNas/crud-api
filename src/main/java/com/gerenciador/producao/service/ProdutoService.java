package com.gerenciador.producao.service;

import com.gerenciador.producao.entity.ProdutoEntity;
import com.gerenciador.producao.enums.TipoProdutoEnum;
import com.gerenciador.producao.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                Long desconto = DESCONTO * item.getPreco() / 100;
                item.setValorComDesconto(item.getPreco() - desconto);
            }
            // elegivel a frete
            if (TipoProdutoEnum.FISICO.name().equals(item.getTipoProduto().name())) {
                item.setValorFrete(item.getPesoKg() * PESO_KG);
            }
        }).toList();

        return produtoList;
    }

    public List<ProdutoEntity> list1() {

        final List<ProdutoEntity> produtosBd = repository.findAll();

        return produtosBd;
    }

    public ProdutoEntity findById(ProdutoEntity entity) {
        return repository.findById(entity.getId()).get();
    }

    public ProdutoEntity put() {
        var entity = new ProdutoEntity();
        if (!repository.existsById(entity.getId())) {

        }
        ProdutoEntity byId = repository.findById(entity.getId()).get();

        byId.setNome(entity.getNome());
        byId.setPreco(entity.getPreco());
        byId.setTipoProduto(entity.getTipoProduto());
        byId.setPesoKg(entity.getPesoKg());
        byId.setPesoMB(entity.getPesoMB());
        byId.setValorFrete(entity.getValorFrete());
        byId.setPromocao(entity.getPromocao());
        byId.setValorComDesconto(entity.getValorComDesconto());
        byId.setValorComFrete(entity.getValorComFrete());

        repository.save(byId);

        return byId;
    }
}
