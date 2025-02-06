package com.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio")
    private final String nome;

    private final String descricao;

    @NotNull(message = "Preço não pode ser nulo")
    @Min(value = 1, message = "Preço não pode ser zero ou negativo")
    private final BigDecimal preco;

    public Produto(String nome, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public BigDecimal getPreco() {
        return this.preco;
    }

    @Override
    public String toString() {
        return String.format("Produto: %d %s Descrição: %s Preço: %.2f", getId(), getNome(), getDescricao(), getPreco());
    }

}
