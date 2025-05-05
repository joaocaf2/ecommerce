package com.ecommerce.controller;

import com.ecommerce.repository.ProdutoRepository;
import com.ecommerce.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MinioService minioService;

    @GetMapping
    public String home(Model model) {
        var produtosComUrlImagemTemporaria = produtoRepository
                .buscarTodos()
                .stream()
                .peek(produto -> produto.setUrlImagem(minioService.montarUrlTemporaria(produto.getUrlImagem())))
                .toList();

        model.addAttribute("produtos", produtosComUrlImagemTemporaria);

        return "index";
    }

}
