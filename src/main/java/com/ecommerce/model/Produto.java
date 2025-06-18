package com.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @Size(max = 100, message = "A descrição deve conter no máximo 100 caracteres")
    private String descricao;

    @Column(columnDefinition = "LONGTEXT")
    private String urlImagem;

    @NotNull(message = "Preço não pode ser nulo")
    @Min(value = 1, message = "Preço não pode ser zero ou negativo")
    private BigDecimal preco;

    public Produto(String nome, String descricao, BigDecimal preco, String urlImagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.urlImagem = urlImagem;
    }

    public Produto() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public void setPreco(BigDecimal preco) {
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

    public String getUrlImagem() {
        return this.urlImagem;
    }

    public BigDecimal getPreco() {
        return this.preco;
    }

    @Override
    public String toString() {
        String mensagemId = (getId() == null) ? "ID não informado" : "ID: " + getId();

        return String.format("Produto: %s | Nome: %s | Descrição: %s | Preço: %.2f", mensagemId, getNome(), getDescricao(), getPreco());
    }
}
