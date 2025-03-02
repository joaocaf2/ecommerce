package com.ecommerce.controller;

import com.ecommerce.model.Produto;
import com.ecommerce.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(HomeController.class)
@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @MockitoBean
    private ProdutoRepository produtoRepository;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @Test
    @DisplayName("Deve passar corretamente os produtos para a view")
    public void devePassarCorretamenteosProdutosParaAView() {
        var bola = new Produto("Bola",
                "Bola de basquete",
                new BigDecimal("350.0"));

        var patinete = new Produto("Patinete",
                "Bola de basquete",
                new BigDecimal("900.0"));

        var bicicleta = new Produto("Bicicleta",
                "Bola de basquete",
                new BigDecimal("1500.0"));

        var frigideira = new Produto("Frigideira",
                "Bola de basquete",
                new BigDecimal("75.0"));

        var produtosDoBanco = List.of(bola, patinete, bicicleta, frigideira);

        when(produtoRepository.buscarTodos()).thenReturn(produtosDoBanco);

        homeController.home(model);

        ArgumentCaptor<List<Produto>> captorValorAtributo = ArgumentCaptor.forClass(List.class);

        verify(model).addAttribute(eq("produtos"), captorValorAtributo.capture());
        List<Produto> produtosDoModel = captorValorAtributo.getValue();

        assertEquals(produtosDoBanco.size(), produtosDoModel.size());
        assertEquals("Bola", produtosDoModel.get(0).getNome());
        assertEquals("Patinete", produtosDoModel.get(1).getNome());
        assertEquals("Bicicleta", produtosDoModel.get(2).getNome());
        assertEquals("Frigideira", produtosDoModel.get(3).getNome());
    }

    @Test
    @DisplayName("Deve retornar a view correta")
    public void deveRetornarAViewCorreta() {
        var view = homeController.home(model);

        assertEquals("index", view);
    }

}
