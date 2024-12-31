package com.gerenciador.producao.controller;

import com.gerenciador.producao.entity.ProdutoEntity;
import com.gerenciador.producao.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping()
    public ProdutoEntity save (@RequestBody ProdutoEntity produto) {
        return service.save(produto);
    }

    @GetMapping()
    public List<ProdutoEntity> list () {
        return service.list();
    }

    @GetMapping("/1")
    public List<ProdutoEntity> list1 () {
        return service.list();
    }

    @GetMapping("/id")
    public ProdutoEntity findById (@RequestBody ProdutoEntity produto) {
        return service.findById(produto);
    }


}
