package com.gerenciador.producao.controller;

import com.gerenciador.producao.dto.AvaregePriceDto;
import com.gerenciador.producao.entity.ProdutoEntity;
import com.gerenciador.producao.service.ProdutoService;
import jakarta.validation.Valid;
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

    @PutMapping()
    public ProdutoEntity put (@RequestBody @Valid ProdutoEntity produto) {
        return service.save(produto);
    }

    @DeleteMapping()
    public void delete (@RequestBody ProdutoEntity produto) {
        service.delete(produto);
    }

    @GetMapping("/by-price")
    public ProdutoEntity byPrice () {
        return service.byPrice();
    }

    @GetMapping("/avarege")
    public AvaregePriceDto avaregePrice () {
        return service.avaregePrice();
    }
}
