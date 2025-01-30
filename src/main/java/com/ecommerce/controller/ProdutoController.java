package com.ecommerce.controller;

import com.ecommerce.model.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @RequestMapping(value = "/formulario")
    public String formulario(Produto produto) {
        return "produtos/formulario-produto";
    }

    @PostMapping(value = "/cadastrar")
    public String cadastrar(Produto produto) {
        return "redirect:/";
    }

}
