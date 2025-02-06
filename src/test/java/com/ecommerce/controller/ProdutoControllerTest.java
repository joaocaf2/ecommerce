package com.ecommerce.controller;

import com.ecommerce.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @DisplayName("Deve chamar corretamente a url de cadastro de produtos")
    public void deveChamarCorretamenteAUrlDeCadastroDeProdutos() throws Exception {
        mockMvc.perform(post("/produtos/cadastrar")
                        .param("nome", "Produto Teste")
                        .param("descricao", "Descrição do produto")
                        .param("preco", "100.00"))
                .andExpect(redirectedUrl("/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("Deve ocasionar erro caso não seja informado o nome do produto")
    public void deveOcasionarErroCasoNaoSejaInformadoONomeDoProduto() throws Exception {
        mockMvc.perform(post("/produtos/cadastrar")
                        .param("nome", "")
                        .param("descricao", "Descrição do produto")
                        .param("preco", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("produtos/formulario-produto"))
                .andExpect(model().attributeHasFieldErrorCode("produto", "nome", "NotEmpty"));
    }

    @ParameterizedTest(name = "Se for informado o valor {0} para o atributo preço o error code deve ser: {1}")
    @CsvSource(delimiter = ';', value = {
            "0;Min",
            "'';NotNull",
    })
    public void deveValidarCorretamenteOAtributoPreco(String valor, String errorCode) throws Exception {
        mockMvc.perform(post("/produtos/cadastrar")
                        .param("nome", "Teste")
                        .param("descricao", "Descrição do produto")
                        .param("preco", valor))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("produtos/formulario-produto"))
                .andExpect(model().attributeHasFieldErrorCode("produto", "preco", errorCode));
    }

}
