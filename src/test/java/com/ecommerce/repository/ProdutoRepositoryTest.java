package com.ecommerce.repository;

import com.ecommerce.model.Produto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName("Deve definir os atributos do produto corretamente")
    public void deveDefinirOsAtributosDoProdutoCorretamente() {
        Produto produto = new Produto("Computador",
                "Um PC da Dell",
                new BigDecimal("1500.0"));

        produtoRepository.salvar(produto);

        var produtoBuscadoNobanco = produtoRepository.buscarPorid(Long.valueOf("1"));

        assertEquals("Produto: ID: 1 | Nome: Computador | Descrição: Um PC da Dell | Preço: 1500.00", produto.toString());
        assertNotNull(produtoBuscadoNobanco);
        assertEquals(Long.valueOf("1"), produtoBuscadoNobanco.getId());
    }

}
