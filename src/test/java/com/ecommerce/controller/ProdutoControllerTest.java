package com.ecommerce.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Página de formulário de cadastro deve ser chamada com sucesso")
    public void deveChamarFormularioDeCadastroProdutoComSucesso() throws Exception {
        mockMvc
                .perform(get("/produtos/cadastrar"))
                .andExpect(status().isOk())
                .andExpect(view().name("produtos/formulario-produto"));
    }
}
