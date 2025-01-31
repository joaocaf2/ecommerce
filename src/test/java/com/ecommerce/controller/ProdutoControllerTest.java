package com.ecommerce.controller;

import com.ecommerce.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoRepository produtoRepository;

    @ParameterizedTest
    @ValueSource(strings = ("/produtos/formulario"))
    @DisplayName("Deve retornar status ok ao acessar as urls do controller")
    public void deveRetornarStatusOkAoAcessarAsUrlsDoController(String url) throws Exception {
        mockMvc
                .perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve cadastrar o produto com sucesso ao submeter o formulário")
    public void deveCadastrarOProdutoComSucessoAoSubmeterOFormulario() throws Exception {
        mockMvc.perform(post("/produtos/cadastrar")
                        .param("nome", "Produto Teste")
                        .param("descricao", "Descrição do produto")
                        .param("preco", "100.00"))
                .andExpect(redirectedUrl("/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

}
