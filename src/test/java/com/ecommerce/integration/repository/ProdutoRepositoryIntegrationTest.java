package com.ecommerce.integration.repository;

import com.ecommerce.config.MinioTestConfig;
import com.ecommerce.model.Produto;
import com.ecommerce.repository.ProdutoRepository;
import com.ecommerce.service.MinioService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Import(MinioTestConfig.class)
public class ProdutoRepositoryIntegrationTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MinioService minioService;

    @Test
    @DisplayName("Deve definir os atributos do produto corretamente")
    public void deveDefinirOsAtributosDoProdutoCorretamente() {
        var produto = new Produto("Computador",
                "Um PC da Dell",
                new BigDecimal("1500.0"),"https://www.exemplo.com.br");

        produtoRepository.salvar(produto);

        var produtoBuscadoNobanco = produtoRepository.buscarPorId(Long.valueOf("1"));

        assertEquals("Produto: ID: 1 | Nome: Computador | Descrição: Um PC da Dell | Preço: 1500.00", produto.toString());
        assertNotNull(produtoBuscadoNobanco);
        assertEquals(Long.valueOf("1"), produtoBuscadoNobanco.getId());
    }

    @Test
    @DisplayName("Deve buscar todos os produtos cadastrados no banco de dados")
    public void deveBuscarTodosOsProdutosCadastrados() {
        var bola = new Produto("Bola",
                "Bola de basquete",
                new BigDecimal("350.0"),"https://www.exemplo.com.br");

        var patinete = new Produto("Patinete",
                "Bola de basquete",
                new BigDecimal("900.0"),"https://www.exemplo.com.br");

        var bicicleta = new Produto("Bicicleta",
                "Bola de basquete",
                new BigDecimal("1500.0"),"https://www.exemplo.com.br");

        var frigideira = new Produto("Frigideira",
                "Bola de basquete",
                new BigDecimal("75.0"),"https://www.exemplo.com.br");

        produtoRepository.salvar(bola);
        produtoRepository.salvar(patinete);
        produtoRepository.salvar(bicicleta);
        produtoRepository.salvar(frigideira);

        var produtos = produtoRepository.buscarTodos();

        assertTrue(produtos.contains(bola));
        assertTrue(produtos.contains(patinete));
        assertTrue(produtos.contains(bicicleta));
        assertTrue(produtos.contains(frigideira));

        assertEquals(4, produtos.size());
    }
}
