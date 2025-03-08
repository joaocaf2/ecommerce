package com.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
public class Produto {

    @Transient
    private final int QTDE_MAX_CARACTERES_URL_IMAGEM = 2048;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @Size(max = 100, message = "A descrição deve conter no máximo 100 caracteres")
    private String descricao;

    @Size(max = QTDE_MAX_CARACTERES_URL_IMAGEM, message = "A url da imagem do produto deve conter no máximo" + QTDE_MAX_CARACTERES_URL_IMAGEM + " caracteres")
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
