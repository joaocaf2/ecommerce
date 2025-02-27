package com.ecommerce.controller;

import com.ecommerce.model.Produto;
import com.ecommerce.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @RequestMapping(value = "/formulario")
    public String formulario(@ModelAttribute Produto produto, Model model) {
        model.addAttribute("produto", produto);

        return "produtos/formulario-produto";
    }

    @PostMapping(value = "/cadastrar")
    public String cadastrar(@Valid @ModelAttribute Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return "produtos/formulario-produto";
        }

        produtoRepository.salvar(produto);
        return "redirect:/";
    }

}
