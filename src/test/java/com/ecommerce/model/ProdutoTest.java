package com.ecommerce.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoTest {

    @Test
    @DisplayName("Deve criar um produto corretamente")
    public void deveCriarUmProdutoCorretamente() {
        Produto produto = new Produto("Bola de basquete",
                "Bola de colecionador",
                new BigDecimal("100.50"));

        assertEquals("Produto: Bola de basquete Descrição: Bola de colecionador Preço: 100.50",
                produto.toString()
        );
    }

}
