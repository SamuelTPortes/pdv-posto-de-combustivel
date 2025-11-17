package com.br.pdvpostocombustivelfrontend.frontend.model;

/**
 * Modelo de dados para Produto
 */
public class Produto {
    private Long id;
    private String nome;
    private String referencia;
    private String fornecedor;
    private String marca;
    private String tipoProduto;

    public Produto() {}

    public Produto(String nome, String referencia, String fornecedor, String marca, String tipoProduto) {
        this.nome = nome;
        this.referencia = referencia;
        this.fornecedor = fornecedor;
        this.marca = marca;
        this.tipoProduto = tipoProduto;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getTipoProduto() { return tipoProduto; }
    public void setTipoProduto(String tipoProduto) { this.tipoProduto = tipoProduto; }

    @Override
    public String toString() {
        return nome + " (" + referencia + ")";
    }
}

