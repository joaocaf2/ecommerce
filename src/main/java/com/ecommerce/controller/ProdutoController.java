package com.ecommerce.controller;

import com.ecommerce.model.Produto;
import com.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @RequestMapping(value = "/formulario")
    public String formulario(Produto produto) {
        return "produtos/formulario-produto";
    }

    @PostMapping(value = "/cadastrar")
    public String cadastrar(@ModelAttribute Produto produto) {
        produtoRepository.salvar(produto);
        return "redirect:/";
    }

}
