package com.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @RequestMapping(value = "/cadastrar")
    public String formulario() {
        return "produtos/formulario-produto";
    }
}
