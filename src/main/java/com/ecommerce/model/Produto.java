package com.ecommerce.model;

import java.math.BigDecimal;

public class Produto {
    private final String nome;
    private final String descricao;
    private final BigDecimal preco;

    public Produto(String nome, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return String.format("Produto: %s Descrição: %s Preço: %.2f", this.nome, this.descricao, this.preco);
    }
}
